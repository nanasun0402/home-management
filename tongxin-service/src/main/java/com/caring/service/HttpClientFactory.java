package com.caring.service;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

/**
 *
 * @author james
 */
@Service
@PropertySources({
    @PropertySource("classpath:http.properties"),
    @PropertySource(value = "classpath:http.dev.properties", ignoreResourceNotFound = true),})
public class HttpClientFactory {

    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private String proxyPort;
    @Value("${proxy.user}")
    private String proxyUser;
    @Value("${proxy.password}")
    private String proxyPassword;

    public CloseableHttpClient createHttpClient() {
        if (null != proxyHost && !proxyHost.isEmpty()) {
            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort));
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            return HttpClients.custom().setRoutePlanner(routePlanner).build();
        }
        return HttpClients.createDefault();
    }
}
