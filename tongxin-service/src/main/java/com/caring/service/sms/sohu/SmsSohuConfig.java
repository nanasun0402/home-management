package com.caring.service.sms.sohu;

import com.caring.service.sms.sohu.sendcloud.sdk.config.Config;
import javax.annotation.PostConstruct;
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
    @PropertySource("classpath:sendcloud.properties"),
    @PropertySource(value = "classpath:sendcloud.dev.properties", ignoreResourceNotFound = true)
})
public class SmsSohuConfig {

    public static final String CHARSET = "utf-8";

    @Value("${server}")
    private String server = "http://www.sendcloud.net";
    // 普通邮件发送
    @Value("${send_api}")
    private String send_api = "http://api.sendcloud.net/apiv2/mail/send";
    // 地址列表发送
    @Value("${send_template_api}")
    private String send_template_api = "http://api.sendcloud.net/apiv2/mail/sendtemplate";
    // 短信发送
    @Value("${send_sms_api}")
    private String send_sms_api = "http://www.sendcloud.net/smsapi/send";
    // 语音发送
    @Value("${send_voice_api}")
    private String send_voice_api = "http://www.sendcloud.net/smsapi/sendVoice";
    // 邮件user
    @Value("${api_user}")
    private String api_user = null;
    // 邮件key
    @Value("${api_key}")
    private String api_key = null;
    // 短信user
    @Value("${sms_user}")
    private String sms_user = null;
    // 短信key
    @Value("${sms_key}")
    private String sms_key = null;

    @Value("${sms_expired}")
    private Long sms_expired;

    @Value("${template_id}")
    private Integer template_id;

    @Value("${inquiry_reject_template_id}")
    private Integer inquiry_reject_template_id;

    @Value("${inquiry_reservation_closed_template_id}")
    private Integer inquiry_reservation_closed_template_id;

    @Value("${inquiry_subsequent_closed_template_id}")
    private Integer inquiry_subsequent_closed_template_id;

    @Value("${inquiry_message_closed_template_id}")
    private Integer inquiry_message_closed_template_id;

    @Value("${inquiry_reservation_agree_template_id}")
    private Integer inquiry_reservation_agree_template_id;

    @Value("${inquiry_subsequent_agree_template_id}")
    private Integer inquiry_subsequent_agree_template_id;

    @Value("${inquiry_message_agree_template_id}")
    private Integer inquiry_message_agree_template_id;

    // 最大收件人数
    public final int MAX_RECEIVERS = 100;
    // 最大地址列表数
    public final int MAX_MAILLIST = 5;
    // 邮件内容大小
    public final int MAX_CONTENT_SIZE = 1024 * 1024;

    @PostConstruct
    public void init() {
        Config.api_user = api_user;
        Config.api_key = api_key;
        Config.sms_user = sms_user;
        Config.sms_key = sms_key;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSend_api() {
        return send_api;
    }

    public void setSend_api(String send_api) {
        this.send_api = send_api;
    }

    public String getSend_template_api() {
        return send_template_api;
    }

    public void setSend_template_api(String send_template_api) {
        this.send_template_api = send_template_api;
    }

    public String getSend_sms_api() {
        return send_sms_api;
    }

    public void setSend_sms_api(String send_sms_api) {
        this.send_sms_api = send_sms_api;
    }

    public String getSend_voice_api() {
        return send_voice_api;
    }

    public void setSend_voice_api(String send_voice_api) {
        this.send_voice_api = send_voice_api;
    }

    public String getApi_user() {
        return api_user;
    }

    public void setApi_user(String api_user) {
        this.api_user = api_user;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSms_user() {
        return sms_user;
    }

    public void setSms_user(String sms_user) {
        this.sms_user = sms_user;
    }

    public String getSms_key() {
        return sms_key;
    }

    public void setSms_key(String sms_key) {
        this.sms_key = sms_key;
    }

    public Long getSms_expired() {
        return sms_expired;
    }

    public void setSms_expired(Long sms_expired) {
        this.sms_expired = sms_expired;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public Integer getInquiry_reject_template_id() {
        return inquiry_reject_template_id;
    }

    public void setInquiry_reject_template_id(Integer inquiry_reject_template_id) {
        this.inquiry_reject_template_id = inquiry_reject_template_id;
    }

    public Integer getInquiry_reservation_closed_template_id() {
        return inquiry_reservation_closed_template_id;
    }

    public void setInquiry_reservation_closed_template_id(Integer inquiry_reservation_closed_template_id) {
        this.inquiry_reservation_closed_template_id = inquiry_reservation_closed_template_id;
    }

    public Integer getInquiry_subsequent_closed_template_id() {
        return inquiry_subsequent_closed_template_id;
    }

    public void setInquiry_subsequent_closed_template_id(Integer inquiry_subsequent_closed_template_id) {
        this.inquiry_subsequent_closed_template_id = inquiry_subsequent_closed_template_id;
    }

    public Integer getInquiry_message_closed_template_id() {
        return inquiry_message_closed_template_id;
    }

    public void setInquiry_message_closed_template_id(Integer inquiry_message_closed_template_id) {
        this.inquiry_message_closed_template_id = inquiry_message_closed_template_id;
    }

    public Integer getInquiry_reservation_agree_template_id() {
        return inquiry_reservation_agree_template_id;
    }

    public void setInquiry_reservation_agree_template_id(Integer inquiry_reservation_agree_template_id) {
        this.inquiry_reservation_agree_template_id = inquiry_reservation_agree_template_id;
    }

    public Integer getInquiry_subsequent_agree_template_id() {
        return inquiry_subsequent_agree_template_id;
    }

    public void setInquiry_subsequent_agree_template_id(Integer inquiry_subsequent_agree_template_id) {
        this.inquiry_subsequent_agree_template_id = inquiry_subsequent_agree_template_id;
    }

    public Integer getInquiry_message_agree_template_id() {
        return inquiry_message_agree_template_id;
    }

    public void setInquiry_message_agree_template_id(Integer inquiry_message_agree_template_id) {
        this.inquiry_message_agree_template_id = inquiry_message_agree_template_id;
    }

}
