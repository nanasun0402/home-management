//package com.caring.wxrs.rest.aspect;
//
//import static com.caring.dao.config.ModelConst.User.CustomerData.HISTORY;
//import static com.caring.dao.config.ModelConst.User.Who.CUSTOMER;
//import com.caring.dao.model.Customer;
//import com.caring.dao.model.Doctor;
//import com.caring.dao.model.Group;
//import com.caring.dao.model.History;
//import com.caring.dao.model.query.Page;
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.builder.template.WxTemplateMessageService;
//import com.caring.wxrs.security.XWxToken;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
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
//public class HistoryAspect extends BaseAspect {
//
//    @AfterReturning(
//            pointcut = "execution(* com.caring.wxrs.rest.HistoryRestController.saveHistory(..))",
//            returning = "result")
//    public void processAfterReturning(JoinPoint joinPoint, Object result) {
//        try {
//            this.executeProcessAfterReturning(joinPoint, result);
//        } catch (Exception e) {
//            LOG.error("History aspect error: {}", e.fillInStackTrace());
//        }
//    }
//
//    private void executeProcessAfterReturning(JoinPoint joinPoint, Object result) {
//        // save a history
//        // customer save, it is a new submit, notify the doctors
//        LOG.debug("HistoryAspect() is running!");
//        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
//        if (result == null) {
//            LOG.warn("Null result, do nothing");
//            return;
//        }
//        XWxToken token = getToken(joinPoint);
//        if (token != null) {
//            switch (token.getType()) {
//                case CUSTOMER: {
//                    History history = (History) result;
//                    if (!checkAndCompleteHistory(history)) {
//                        LOG.error("Invalid inquiry without mandatory information: {}", ServiceUtils.toJson(history));
//                        return;
//                    }
//                    // Customer save a history information notifiy the doctors of the group
//                    Group group = history.getCustomer().getGroup();
//                    Page<Doctor> doctorsPage = doctorService.findDoctorsByGroupId(group.getId(), null);
//                    LOG.debug("Found {} doctors to notify for followup submitted", doctorsPage.getTotal());
//                    doctorsPage.getContent().forEach(doctor -> {
//                        if (doctor.getWechat() != null) {
//                            WxTemplateMessageService.WxTemplateDataBuilder builder = super.wxTemplateMessageService.getSubmitMessageBuilder(HISTORY);
//                            if (builder != null) {
//                                WxMpTemplateMessage message = builder.build(history);
//                                message.setToUser(doctor.getWechat().getOpenId());
//                                message.setUrl(getCallbackUrl("/history/" + history.getId() + "/reply"));
//                                sendTemplateMessage(message);
//                            }
//                        }
//                    });
//                    break;
//                }
//            }
//        }
//        LOG.debug("Method returned value is : " + ServiceUtils.toJson(result));
//        LOG.debug("******");
//    }
//
//    private boolean checkAndCompleteHistory(History history) {
//        if (history.getCustomer() == null) {
//            return false;
//        }
//        Customer customer = super.customerService.findCustomerById(history.getCustomer().getId());
//        history.setCustomer(customer);
//        return !(history.getCustomer().getGroup() == null);
//    }
//}
