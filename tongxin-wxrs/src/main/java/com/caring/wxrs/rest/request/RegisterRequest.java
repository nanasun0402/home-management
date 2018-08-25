package com.caring.wxrs.rest.request;

/**
 *
 * @author james
 */
public class RegisterRequest {

    private String who;
    private String code;
    private String mobile;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
