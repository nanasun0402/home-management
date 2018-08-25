//package com.caring.wxrs.rest.aspect;
//
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_AGREE;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_CLOSED;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_REJECT;
//import static com.caring.dao.config.ModelConst.User.Who.CUSTOMER;
//import static com.caring.dao.config.ModelConst.User.Who.DOCTOR;
//import static com.caring.dao.config.ModelConst.User.Who.USER;
//import com.caring.dao.model.Customer;
//import com.caring.dao.model.Doctor;
//import com.caring.dao.model.Group;
//import com.caring.dao.model.Inquiry;
//import com.caring.service.ServiceUtils;
//import com.caring.service.sms.sohu.sendcloud.sdk.model.SendCloudSms;
//import com.caring.service.wx.builder.template.SmsTemplateMessageService.SmsTemplateDataBuilder;
//import com.caring.service.wx.builder.template.WxTemplateMessageService.WxTemplateDataBuilder;
//import com.caring.wxrs.security.XWxToken;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author james
// */
//@Aspect
//@Component
//public class InquiryAspect extends BaseAspect {
//
//    @AfterReturning(
//            pointcut = "execution(* com.caring.wxrs.rest.InquiryRestController.saveInquiry(..))",
//            returning = "result")
//    public void processAfterReturning(JoinPoint joinPoint, Object result) {
//        try {
//            this.executeProcessAfterReturning(joinPoint, result);
//        } catch (Exception e) {
//            LOG.error("Inquiry aspect error: {}", e.fillInStackTrace());
//        }
//    }
//
//    private void executeProcessAfterReturning(JoinPoint joinPoint, Object result) {
//        // save a inquiry
//        // customer save, it is a new submit, notify the doctor of the group creator
//        // doctor/aissistant save, it is a reply, notify the customer the result reject/closed
//        // from the inquiry type, to generate different wechat notification
//        LOG.debug("InquiryAspect() is running!");
//        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
//        if (result == null) {
//            LOG.warn("Null result, do nothing");
//            return;
//        }
//        Inquiry inquiry = (Inquiry) result;
//        XWxToken token = getToken(joinPoint);
//        if (token != null) {
//            switch (token.getType()) {
//                case USER: {
//                    if (StringUtils.equals(INQUIRY_REJECT, inquiry.getStatus())) {
//                        if (!checkInquiryForCustomer(inquiry)) {
//                            LOG.error("Invalid inquiry without mandatory information: {}", ServiceUtils.toJson(inquiry));
//                            return;
//                        }
//
//                        SmsTemplateDataBuilder smsBuilder = smsTemplateMessageService.getSmsMessageBuilder(INQUIRY_REJECT);
//                        if (smsBuilder != null) {
//                            SendCloudSms sms = smsBuilder.build(inquiry);
//                            smsSohuService.sendSmsSlient(sms);
//                        }
//                        WxTemplateDataBuilder builder = wxTemplateMessageService.getClosedMessageBuilder(INQUIRY_REJECT);
//                        if (builder != null) {
//                            WxMpTemplateMessage message = builder.build(inquiry);
//                            message.setUrl(getCallbackUrl("/inquiry/" + inquiry.getId()));
//                            sendTemplateMessage(message);
//                        } else {
//                            LOG.warn("No message builder is found: {}", inquiry.getType());
//                        }
//                    } else if (StringUtils.equals(INQUIRY_AGREE, inquiry.getStatus())) {
//                        if (!checkAndCompleteInquiryForDoctor(inquiry)) {
//                            LOG.error("Invalid inquiry without mandatory information: {}", ServiceUtils.toJson(inquiry));
//                            return;
//                        }
//
//                        SmsTemplateDataBuilder smsBuilder = smsTemplateMessageService.getSmsMessageBuilder(INQUIRY_AGREE);
//                        if (smsBuilder != null) {
//                            SendCloudSms sms = smsBuilder.build(inquiry);
//                            //  set group creator mobile for message
//                            Group group = inquiry.getCustomer().getGroup();
//                            Doctor doctor = doctorService.findDoctorById(group.getCreatorDoctorId());
//                            if (doctor != null) {
//                                sms.addPhone(doctor.getMobile());
//                                smsSohuService.sendSmsSlient(sms);
//                            } else {
//                                LOG.error("Invalid doctor id: {}", group.getCreatorDoctorId());
//                            }
//                        }
//
//                        WxTemplateDataBuilder builder = wxTemplateMessageService.getSubmitMessageBuilder(inquiry.getType());
//                        if (builder != null) {
//                            WxMpTemplateMessage message = builder.build(inquiry);
//                            //  set group creator openid for message
//                            Group group = inquiry.getCustomer().getGroup();
//                            Doctor doctor = doctorService.findDoctorById(group.getCreatorDoctorId());
//                            if (doctor != null) {
//                                message.setToUser(doctor.getWechat().getOpenId());
//                                message.setUrl(getCallbackUrl("/inquiry/" + inquiry.getId() + "/reply"));
//                                sendTemplateMessage(message);
//                            } else {
//                                LOG.error("Invalid doctor id: {}", group.getCreatorDoctorId());
//                            }
//                        } else {
//                            LOG.warn("No message builder is found: {}", inquiry.getType());
//                        }
//                    } else {
//                        LOG.info("The inquiry is not agreed, so doctor will not be notified");
//                    }
//                }
//                case DOCTOR: {
//                    if (StringUtils.equals(INQUIRY_CLOSED, inquiry.getStatus())) {
//                        if (!checkInquiryForCustomer(inquiry)) {
//                            LOG.error("Invalid inquiry without mandatory information: {}", ServiceUtils.toJson(inquiry));
//                            return;
//                        }
//
//                        SmsTemplateDataBuilder smsBuilder = smsTemplateMessageService.getSmsMessageBuilder(INQUIRY_CLOSED);
//                        if (smsBuilder != null) {
//                            SendCloudSms sms = smsBuilder.build(inquiry);
//                            smsSohuService.sendSmsSlient(sms);
//                        }
//
//                        WxTemplateDataBuilder builder = wxTemplateMessageService.getClosedMessageBuilder(inquiry.getType());
//                        if (builder != null) {
//                            WxMpTemplateMessage message = builder.build(inquiry);
//                            message.setUrl(getCallbackUrl("/inquiry/" + inquiry.getId()));
//                            sendTemplateMessage(message);
//                        } else {
//                            LOG.warn("No message builder is found: {}", inquiry.getType());
//                        }
//                    } else {
//                        LOG.error("Not need notification");
//                    }
//                    break;
//                }
//                case CUSTOMER:
//                    LOG.error("Customer submit do nothing for notification: {}", token);
//                    break;
//                default:
//                    // DO nothing only report an error message
//                    LOG.error("Not supported: {}", token);
//            }
//        }
//        LOG.debug("Method returned value is : " + ServiceUtils.toJson(result));
//        LOG.debug("******");
//    }
//
//    private boolean checkAndCompleteInquiryForDoctor(Inquiry inquiry) {
//        // An inquiry must be in a group, it must has customer, doctor and customer/doctor must has a group
//        if (inquiry.getCustomer() == null) {
//            return false;
//        }
//        Customer customer = super.customerService.findCustomerById(inquiry.getCustomer().getId());
//        inquiry.setCustomer(customer);
//        return !(customer.getGroup() == null
//                || customer.getGroup().getCreatorDoctorId() == null);
//    }
//
//    private boolean checkInquiryForCustomer(Inquiry inquiry) {
//        return inquiry.getCustomer() != null;
//    }
//
//}
