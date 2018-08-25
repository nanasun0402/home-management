package com.caring.wxrs;

import com.caring.service.ServiceUtils;
import com.fasterxml.jackson.databind.util.RawValue;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 *
 * @author james
 */
@ControllerAdvice
public class TongxinJsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public TongxinJsonpResponseBodyAdvice() {
        super("rest controller response handler rewriter");
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue mjv, MediaType mt, MethodParameter mp, ServerHttpRequest shr, ServerHttpResponse shr1) {
        // Filter out defined properties in the defined class
        // to avoid too deep nested structure
        Object value = mjv.getValue();
        if (mp.hasMethodAnnotation(JsonProperties.class) && value != null) {
            LOG.debug("beforeBodyWriteInternal::{}", ServiceUtils.toJson(mjv));
            Map<Class<?>, Set<String>> includeMap = Maps.newHashMap();
            Map<Class<?>, Set<String>> excludeMap = Maps.newHashMap();
            Annotation[] annotations = mp.getMethodAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof JsonProperties) {
                    JsonProperties jip = (JsonProperties) annotation;
                    JsonProperty[] properties = jip.value();
                    if (properties != null) {
                        for (JsonProperty propertyRule : properties) {
                            if (propertyRule.excludeProperties() != null) {
                                excludeMap.put(propertyRule.affectClass(), Sets.newHashSet(propertyRule.excludeProperties()));
                            }
                            if (propertyRule.includeProperties() != null) {
                                includeMap.put(propertyRule.affectClass(), Sets.newHashSet(propertyRule.includeProperties()));
                            }
                        }
                    }
                }
            }

            if (!excludeMap.isEmpty()) {
                mjv.setValue(new RawValue(JsonHelper.toJSON(value, includeMap, excludeMap).toString()));
            }
        } else if (value == null && mt.isCompatibleWith(MediaType.APPLICATION_JSON_UTF8)) {
            mjv.setValue(ServiceUtils.fromJson("{}"));
            LOG.debug("set response as '{}'");
        }
    }

}
