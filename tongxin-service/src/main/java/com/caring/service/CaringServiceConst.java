package com.caring.service;

/**
 *
 * @author james
 */
public interface CaringServiceConst {

    String CALLBACK_USERINFO_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
    String DOCTOR_ASSISTANT_URL = "http://mp.weixin.qq.com/s?__biz=MzI5NTg5MTIzNw==&mid=100000014&idx=1&sn=bbda89e6cd07ee00089f09ad3b0e8f27&chksm=6c4df9445b3a7052188fea5019f98f369656d3587daad224d497ad082146338057033674fa9a#rd";
    String QRCODE_DOCTORID_PREFIX = "qrscene_doctor_";
    String DOCTORID_PREFIX = "doctor_";
    String WECHAT_DOMAIN = "wechat.domain";
    String DOMAIN_SUIFANG = "suifang";
    String DOMAIN_XIANXIN = "xianxin";

    String MOCK_OPEN_ID_TOKEN = "openid--";
    String MOCK_DOCTOR_TOKEN = "-doctor-";
}
