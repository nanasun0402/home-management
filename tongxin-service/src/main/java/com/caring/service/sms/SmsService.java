package com.caring.service.sms;

/**
 *
 * @author james
 */
public interface SmsService {

    public String sendSmsCode(String mobile, boolean backdoor);

    public void checkSmsCode(String mobile, String code);
}
