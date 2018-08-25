package com.caring.dao.model.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author james
 */
public class PageFilter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @JsonIgnore
    private String externalFilter;
    @JsonIgnore
    private Map<String, Object> params;

    protected void handleAND(StringBuilder strBuilder) {
        if (strBuilder.length() > 0) {
            strBuilder.append(" AND ");
        }
    }

    protected void handleOR(StringBuilder strBuilder) {
        if (strBuilder.length() > 0) {
            strBuilder.append(" OR ");
        }
    }

    protected String handleLIKE(String value) {
        return '%' + value + '%';
    }

    protected PageFilter withExtenalFilter(String externalFilter) {
        LOG.debug("Filter build: {}", externalFilter);
        this.externalFilter = externalFilter;
        return this;
    }

    protected PageFilter getNextFilter() {
        return null;
    }

    public String getExternalFilter() {
        return externalFilter;
    }

    public boolean hasExternalFilter() {
        return StringUtils.isNotEmpty(this.externalFilter);
    }

    public PageFilter buildFilter() {
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public boolean hasParams() {
        return params != null && !params.isEmpty();
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public PageFilter withParameter(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    public Query fillQueryParameters(Query query) {
        if (getNextFilter() != null) {
            getNextFilter().fillQueryParameters(query);
        }
        if (getParams() != null && !getParams().isEmpty()) {
            getParams().entrySet().forEach(param -> {
                LOG.debug("Query Parameter: {}: {}", param.getKey(), param.getValue());
                query.setParameter(param.getKey(), param.getValue());
            });
        }
        return query;
    }
}
