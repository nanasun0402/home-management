package com.caring.service.sms.two35;

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
    @PropertySource("classpath:sms.235.properties"),
    @PropertySource(value = "classpath:sms.235.dev.properties", ignoreResourceNotFound = true)
})
public class Sms235Config {

    @Value("${235.api.url}")
    private String apiURL;

    @Value("${235.api.user}")
    private String apiUser;

    @Value("${235.api.pwd}")
    private String apiPwd;

    @Value("${235.api.expired}")
    private Long expiredTime;

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiPwd() {
        return apiPwd;
    }

    public void setApiPwd(String apiPwd) {
        this.apiPwd = apiPwd;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

}
