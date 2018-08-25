package com.caring.service.sms.sohu.sendcloud.sdk.demo;

import java.io.IOException;

import org.apache.http.ParseException;

import com.caring.service.sms.sohu.sendcloud.sdk.builder.SendCloudBuilder;
import com.caring.service.sms.sohu.sendcloud.sdk.core.SendCloud;
import com.caring.service.sms.sohu.sendcloud.sdk.exception.VoiceException;
import com.caring.service.sms.sohu.sendcloud.sdk.model.SendCloudVoice;
import com.caring.service.sms.sohu.sendcloud.sdk.util.ResponseData;

public class SendVoice {

    public static void send() throws ParseException, IOException, VoiceException {
        SendCloudVoice voice = new SendCloudVoice();
        voice.setCode("123456");
        voice.setPhone("12345678910;12345678911");

        SendCloud sc = SendCloudBuilder.build();
        ResponseData res = sc.sendVoice(voice);

        System.out.println(res.getResult());
        System.out.println(res.getStatusCode());
        System.out.println(res.getMessage());
        System.out.println(res.getInfo());
    }

    public static void main(String[] args) throws Throwable {
        send();
    }
}
