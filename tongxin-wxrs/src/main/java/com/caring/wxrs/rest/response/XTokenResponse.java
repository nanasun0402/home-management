package com.caring.wxrs.rest.response;

import static com.caring.dao.config.ModelConst.User.Who.CUSTOMER;
import static com.caring.dao.config.ModelConst.User.Who.DOCTOR;
import static com.caring.dao.config.ModelConst.User.Who.USER;
import com.caring.dao.model.User;
import java.util.Map;

/**
 *
 * @author james
 */
public class XTokenResponse {

    private String domain;
    private Map.Entry<String, String> token;
    private String type;
    private User user;

    public XTokenResponse(String domain, Map.Entry<String, String> token, String type, User user) {
        this.domain = domain;
        this.token = token;
        this.type = type;
        this.user = user;
    }

//    public XTokenResponse(String domain, Map.Entry<String, String> token, String type, Doctor doctor, Customer customer) {
//        this(domain, token, type, doctor, customer, null);
//    }

    public XTokenResponse(String domain, Map.Entry<String, String> token, User user) {
        this(domain, token, USER, user);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Map.Entry<String, String> getToken() {
        return token;
    }

    public void setToken(Map.Entry<String, String> token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
