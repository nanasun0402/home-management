package com.caring.service.wx.config;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
    @PropertySource("classpath:wechat.mp.properties"),
    @PropertySource(value = "classpath:wechat.dev.mp.properties", ignoreResourceNotFound = true)
})
public class WechatMpProperties {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    /**
     * 设置微信公众号的appid
     */
    @Value("${appId}")
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    @Value("${secret}")
    private String secret;

    /**
     * 设置微信公众号的token
     */
    @Value("${token}")
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    @Value("${aesKey}")
    private String aesKey;

    @Value("${wxRedirectDomain}")
    private String wxRedirectDomain;

    @Value("${httpProxyHost}")
    private String httpProxyHost;

    @Value("${httpProxyPort}")
    private String httpProxyPort;

    @Value("${wxMock}")
    private Boolean wxMock;

    @Value("${wxMenu}")
    private String wxMenu;

    @Value("${wxMenuCustomer}")
    private String wxMenuCustomer;

    @Value("${wxMenuDoctor}")
    private String wxMenuDoctor;

    @Value("${redirectParams}")
    private String redirectParams;

    private Map<String, String> redirectMapping;

    @PostConstruct
    public void init() {
        LOG.info("Init WX ... ");
        LOG.info("redirectParams: {}", redirectParams);
        if (StringUtils.isNotEmpty(redirectParams)) {
            redirectMapping = new HashMap<>();
            String[] params = redirectParams.split("\\|");
            for (String param : params) {
                String key = param.substring(0, param.indexOf("="));
                String value = param.substring(param.indexOf("=") + 1);
                LOG.info("key: {}, value: {}", key, value);
                redirectMapping.put(key.trim(), value.trim());
            }
        }
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getWxRedirectDomain() {
        return wxRedirectDomain;
    }

    public void setWxRedirectDomain(String wxRedirectDomain) {
        this.wxRedirectDomain = wxRedirectDomain;
    }

    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    public String getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(String httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public Boolean getWxMock() {
        return wxMock;
    }

    public void setWxMock(Boolean wxMock) {
        this.wxMock = wxMock;
    }

    public String getWxMenu() {
        return wxMenu;
    }

    public void setWxMenu(String wxMenu) {
        this.wxMenu = wxMenu;
    }

    public String getWxMenuCustomer() {
        return wxMenuCustomer;
    }

    public void setWxMenuCustomer(String wxMenuCustomer) {
        this.wxMenuCustomer = wxMenuCustomer;
    }

    public String getWxMenuDoctor() {
        return wxMenuDoctor;
    }

    public void setWxMenuDoctor(String wxMenuDoctor) {
        this.wxMenuDoctor = wxMenuDoctor;
    }

    public Map<String, String> getRedirectMapping() {
        return redirectMapping;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
