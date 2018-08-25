//package com.caring.wxrs.rest.aspect;
//
//import static com.caring.dao.config.ModelConst.User.Inquiry.ILLNESS_REQUIRED;
//import static com.caring.dao.config.ModelConst.User.Who.CUSTOMER;
//import com.caring.dao.model.Customer;
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.builder.template.WxTemplateMessageService;
//import com.caring.wxrs.rest.response.XTokenResponse;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author james
// */
//@Aspect
//@Component
//public class RegisterAspect extends BaseAspect {
//
//    @AfterReturning(
//            pointcut = "execution(* com.caring.wxrs.rest.RegisterRestController.doRegister(..))",
//            returning = "result")
//    public void processAfterReturning(JoinPoint joinPoint, Object result) {
//        try {
//            this.executeProcessAfterReturning(joinPoint, result);
//        } catch (Exception e) {
//            LOG.error("Register aspect error: {}", e.fillInStackTrace());
//        }
//    }
//
//    public void executeProcessAfterReturning(JoinPoint joinPoint, Object result) {
//        // customer register, reminder customer fill the illness information
//        LOG.debug("RegisterAspect() is running!");
//        LOG.debug("hijacked : " + joinPoint.getSignature().getName());
//        if (result == null) {
//            LOG.warn("Null result, do nothing");
//            return;
//        }
//        ResponseEntity<XTokenResponse> response = (ResponseEntity<XTokenResponse>) result;
//        if (CUSTOMER.equals(response.getBody().getType())) {
//            Customer customer = response.getBody().getCustomer();
//            String openId = customer.getWechat().getOpenId();
//            WxTemplateMessageService.WxTemplateDataBuilder builder = wxTemplateMessageService.getSubmitMessageBuilder(ILLNESS_REQUIRED);
//            if (builder != null) {
//                WxMpTemplateMessage message = builder.build(customer);
//                message.setToUser(openId);
//                message.setUrl(getCallbackUrl(String.format("/patient/%d/illness", customer.getId())));
//                sendTemplateMessage(message);
//            }
//        }
//
//        // Customer save a followup information notifiy the doctors of the group
//        LOG.debug("Method returned value is : " + ServiceUtils.toJson(result));
//        LOG.debug("******");
//    }
//
//}
