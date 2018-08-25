package com.caring.wxrs.rest.response;

/**
 *
 * @author james
 */
public class DoctorQrCodeResponse {

    private String qrCodeUrl;

    public DoctorQrCodeResponse(String url) {
        this.qrCodeUrl = url;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

}
