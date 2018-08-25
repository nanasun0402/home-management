//package com.caring.wxrs.rest.aspect;
//
//import static com.caring.dao.config.ModelConst.User.CustomerData.FOLLOWUP;
//import com.caring.dao.model.Customer;
//import com.caring.dao.model.Doctor;
//import com.caring.dao.model.Followup;
//import com.caring.dao.model.Group;
//import com.caring.dao.model.query.Page;
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.builder.template.WxTemplateMessageService;
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
//public class FollowupAspect extends BaseAspect {
//
//    @AfterReturning(
//            pointcut = "execution(* com.caring.wxrs.rest.FollowupRestController.saveFollowup(..))",
//            returning = "result")
//    public void processAfterReturning(JoinPoint joinPoint, Object result) {
//        try {
//            this.executeProcessAfterReturning(joinPoint, result);
//        } catch (Exception e) {
//            LOG.error("Followup aspect error: {}", e.fillInStackTrace());
//        }
//    }
//
//    private void executeProcessAfterReturning(JoinPoint joinPoint, Object result) {
//        // save a followup
//        // customer save, it is a new submit, notify the doctors
//        LOG.debug("FollowupAspect() is running!");
//        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
//        if (result == null) {
//            LOG.warn("Null result, do nothing");
//            return;
//        }
//        Followup followup = (Followup) result;
//        if (!checkAndCompleteFollowup(followup)) {
//            LOG.error("Invalid inquiry without mandatory information: {}", ServiceUtils.toJson(followup));
//            return;
//        }
//        // Customer save a followup information notifiy the doctors of the group
//        Group group = followup.getCustomer().getGroup();
//        Page<Doctor> doctorsPage = doctorService.findDoctorsByGroupId(group.getId(), null);
//        LOG.debug("Found {} doctors to notify for followup submitted", doctorsPage.getTotal());
//        doctorsPage.getContent().forEach(doctor -> {
//            if (doctor.getWechat() != null) {
//                WxTemplateMessageService.WxTemplateDataBuilder builder = super.wxTemplateMessageService.getSubmitMessageBuilder(FOLLOWUP);
//                if (builder != null) {
//                    WxMpTemplateMessage message = builder.build(followup);
//                    message.setToUser(doctor.getWechat().getOpenId());
//                    message.setUrl(getCallbackUrl("/followup/" + followup.getId()));
//                    sendTemplateMessage(message);
//                }
//            }
//        });
//        LOG.debug("Method returned value is : " + ServiceUtils.toJson(result));
//        LOG.debug("******");
//    }
//
//    private boolean checkAndCompleteFollowup(Followup followup) {
//        if (followup.getCustomer() == null) {
//            return false;
//        }
//        Customer customer = super.customerService.findCustomerById(followup.getCustomer().getId());
//        followup.setCustomer(customer);
//        return !(followup.getCustomer().getGroup() == null);
//    }
//}
