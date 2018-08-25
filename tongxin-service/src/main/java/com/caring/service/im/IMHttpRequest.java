package com.caring.service.im;

import com.caring.service.CaringServiceException;
import com.caring.service.HttpClientFactory;
import com.caring.service.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author james
 */
@Component
public class IMHttpRequest {

    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private HttpClientFactory httpClientFactory;

    private String user;

    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IMResult post(String postUrl, IMExchangeMessage postMessage) {
        try (CloseableHttpClient http = httpClientFactory.createHttpClient()) {
            String imCallUrl = UriComponentsBuilder.fromUriString(postUrl).toUriString();
            HttpPost post = new HttpPost(imCallUrl);
            ByteArrayEntity entity = new ByteArrayEntity(ServiceUtils.toJson(postMessage).getBytes("UTF-8"));
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            post.setEntity(entity);
            try (CloseableHttpResponse response = http.execute(post)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String responseStr = EntityUtils.toString(response.getEntity());
                    return objectMapper.readValue(responseStr, IMResult.class);
                } else {
                    log.error("Request:" + ServiceUtils.toJson(postMessage));
                    log.error("Response:" + EntityUtils.toString(response.getEntity()));
                    throw new CaringServiceException(response.getStatusLine().toString());
                }
            }
        } catch (CaringServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Request:" + ServiceUtils.toJson(postMessage));
            log.error(e.fillInStackTrace());
            throw new CaringServiceException(e);
        }
    }

}
