//package com.caring.service.wx.builder.template;
//
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_AGREE;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_CLOSED;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_REJECT;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_MESSAGE;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_RESERVATION;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_SUBSEQUENT;
//import com.caring.dao.model.BaseEntity;
//import com.caring.dao.model.Inquiry;
//import com.caring.service.ServiceUtils;
//import com.caring.service.sms.sohu.SmsSohuConfig;
//import com.caring.service.sms.sohu.sendcloud.sdk.model.SendCloudSms;
//import com.google.common.collect.ImmutableMap;
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author james
// */
//@Service
//public class SmsTemplateMessageService {
//
//    @Autowired
//    private SmsSohuConfig sohuConfig;
//
//    private Map<String, SmsTemplateDataBuilder> MESSAGE_SUBMIT_LIST;
//
//    @PostConstruct
//    public void initService() {
//        MESSAGE_SUBMIT_LIST = ImmutableMap.<String, SmsTemplateDataBuilder>builder()
//                .put(INQUIRY_REJECT, new InquiryRejectSmsMessageBuilder(sohuConfig))
//                .put(INQUIRY_AGREE, new InquiryAgreeSmsMessageBuilder(sohuConfig))
//                .put(INQUIRY_CLOSED, new InquiryClosedSmsMessageBuilder(sohuConfig))
//                .build();
//    }
//
//    public SmsTemplateDataBuilder getSmsMessageBuilder(String type) {
//        return MESSAGE_SUBMIT_LIST.get(type);
//    }
//
//    public static interface SmsTemplateDataBuilder {
//
//        SendCloudSms build(BaseEntity entity);
//    }
//
//    public static class InquiryRejectSmsMessageBuilder implements SmsTemplateDataBuilder {
//
//        private final SmsSohuConfig sohuConfig;
//
//        public InquiryRejectSmsMessageBuilder(SmsSohuConfig sohuConfig) {
//            this.sohuConfig = sohuConfig;
//        }
//
//        @Override
//        public SendCloudSms build(BaseEntity entity) {
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SendCloudSms sms = new SendCloudSms();
//                sms.setMsgType(0);
//                sms.setTemplateId(sohuConfig.getInquiry_reject_template_id());
//                sms.addPhone(inquiry.getCustomer().getMobile());
//                sms.addVars("code", inquiry.getId().toString());
//                return sms;
//            }
//            return null;
//        }
//    }
//
//    public static class InquiryClosedSmsMessageBuilder implements SmsTemplateDataBuilder {
//
//        private final SmsSohuConfig sohuConfig;
//
//        public InquiryClosedSmsMessageBuilder(SmsSohuConfig sohuConfig) {
//            this.sohuConfig = sohuConfig;
//        }
//
//        @Override
//        public SendCloudSms build(BaseEntity entity) {
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                if (null != inquiry.getType()) {
//                    SendCloudSms sms = new SendCloudSms();
//                    sms.setMsgType(0);
//                    sms.addPhone(inquiry.getCustomer().getMobile());
//                    sms.addVars("code", inquiry.getId().toString());
//                    switch (inquiry.getType()) {
//                        case INQUIRY_TYPE_MESSAGE:
//                            // 留言问诊
//                            sms.setTemplateId(sohuConfig.getInquiry_message_closed_template_id());
//                            break;
//                        case INQUIRY_TYPE_RESERVATION:
//                            // 电话预约
//                            // 您的电话问诊预约[咨询号-%code%]已预约成功，接听电话时间为%time%，请在此时间前后半小时内注意接听，谢谢【医相随】
//                            SimpleDateFormat sdf1 = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                            sms.setTemplateId(sohuConfig.getInquiry_reservation_closed_template_id());
//                            sms.addVars("time", sdf1.format(inquiry.getAnswer().getReceiveTime()));
//                            break;
//                        case INQUIRY_TYPE_SUBSEQUENT:
//                            // 留言复诊
//                            // 您的复诊预约[咨询号-%code%]已预约成功，就诊时间为%time%，
//                            // 就诊地址为：%hospital%，%department%，门诊医生为：%doctor%，请准时就诊，谢谢【医相随】
//                            SimpleDateFormat sdf2 = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                            sms.setTemplateId(sohuConfig.getInquiry_subsequent_closed_template_id());
//                            sms.addVars("time", sdf2.format(inquiry.getAnswer().getReceiveTime()));
//                            sms.addVars("hospital", inquiry.getAnswer().getHospitalName());
//                            sms.addVars("department", inquiry.getAnswer().getOfficeName());
//                            sms.addVars("doctor", inquiry.getAnswer().getDoctorName());
//                            break;
//                        default:
//                            return null;
//                    }
//                    return sms;
//                }
//            }
//            return null;
//        }
//
//    }
//
//    public static class InquiryAgreeSmsMessageBuilder implements SmsTemplateDataBuilder {
//
//        private final SmsSohuConfig sohuConfig;
//
//        public InquiryAgreeSmsMessageBuilder(SmsSohuConfig sohuConfig) {
//            this.sohuConfig = sohuConfig;
//        }
//
//        @Override
//        public SendCloudSms build(BaseEntity entity) {
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                if (null != inquiry.getType()) {
//                    SendCloudSms sms = new SendCloudSms();
//                    sms.setMsgType(0);
//                    sms.addVars("code", inquiry.getId().toString());
//                    switch (inquiry.getType()) {
//                        // 留言问诊
//                        case INQUIRY_TYPE_MESSAGE:
//                            sms.setTemplateId(sohuConfig.getInquiry_message_agree_template_id());
//                            break;
//                        // 电话预约
//                        case INQUIRY_TYPE_RESERVATION:
//                            sms.setTemplateId(sohuConfig.getInquiry_reservation_agree_template_id());
//                            break;
//                        // 留言复诊
//                        case INQUIRY_TYPE_SUBSEQUENT:
//                            sms.setTemplateId(sohuConfig.getInquiry_subsequent_agree_template_id());
//                            break;
//                        default:
//                            return null;
//                    }
//                    return sms;
//                }
//            }
//            return null;
//        }
//
//    }
//}
