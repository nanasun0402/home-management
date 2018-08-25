package com.caring.service.im;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 *
 * @author james
 */
@Component
@PropertySources({
    @PropertySource("classpath:im.properties"),
    @PropertySource(value = "classpath:im.dev.properties", ignoreResourceNotFound = true)
})
public class IMConfig {

    private final Logger log = Logger.getLogger(getClass());

    public final static long WX_IMAGE_MAXSIZE = 2 * 1024 * 1024; //2M

    @Value("${imservice.host}")
    private String imServiceHost;

    public String getImServiceHost() {
        return imServiceHost;
    }

    public void setImServiceHost(String imServiceHost) {
        this.imServiceHost = imServiceHost;
    }
}
