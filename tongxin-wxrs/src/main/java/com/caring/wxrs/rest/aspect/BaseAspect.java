//package com.caring.wxrs.rest.aspect;
//
//import com.caring.dao.service.CustomerService;
//import com.caring.dao.service.DoctorService;
//import com.caring.dao.service.GroupService;
//import static com.caring.service.CaringServiceConst.CALLBACK_USERINFO_URL;
//import com.caring.service.ServiceUtils;
//import com.caring.service.sms.sohu.SmsSohuService;
//import com.caring.service.wx.builder.template.SmsTemplateMessageService;
//import com.caring.service.wx.builder.template.WxTemplateMessageService;
//import com.caring.service.wx.config.WechatMpProperties;
//import com.caring.wxrs.security.TokenHandler;
//import com.caring.wxrs.security.XWxToken;
//import me.chanjar.weixin.common.exception.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *
// * @author james
// */
//public class BaseAspect {
//
//    protected final Logger LOG = LoggerFactory.getLogger(getClass());
//    protected TokenHandler authenticateTokenHandler;
//    protected WxTemplateMessageService wxTemplateMessageService;
//    protected SmsTemplateMessageService smsTemplateMessageService;
//    protected SmsSohuService smsSohuService;
//    protected WechatMpProperties wechatMpProperties;
//    protected WxMpService wxService;
//    protected DoctorService doctorService;
//    protected CustomerService customerService;
//    protected GroupService groupService;
//
//    @Autowired
//    public void setAuthenticateTokenHandler(TokenHandler authenticateTokenHandler) {
//        this.authenticateTokenHandler = authenticateTokenHandler;
//    }
//
//    @Autowired
//    public void setWxTemplateMessageService(WxTemplateMessageService wxTemplateMessageService) {
//        this.wxTemplateMessageService = wxTemplateMessageService;
//    }
//
//    @Autowired
//    public void setSmsSohuService(SmsSohuService smsSohuService) {
//        this.smsSohuService = smsSohuService;
//    }
//
//    @Autowired
//    public void setSmsTemplateMessageService(SmsTemplateMessageService smsTemplateMessageService) {
//        this.smsTemplateMessageService = smsTemplateMessageService;
//    }
//
//    @Autowired
//    public void setWechatMpProperties(WechatMpProperties wechatMpProperties) {
//        this.wechatMpProperties = wechatMpProperties;
//    }
//
//    @Autowired
//    public void setWxService(WxMpService wxService) {
//        this.wxService = wxService;
//    }
//
//    @Autowired
//    public void setDoctorService(DoctorService doctorService) {
//        this.doctorService = doctorService;
//    }
//
//    @Autowired
//    public void setCustomerService(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @Autowired
//    public void setGroupService(GroupService groupService) {
//        this.groupService = groupService;
//    }
//
//    protected void sendTemplateMessage(WxMpTemplateMessage message) {
//        try {
//            wxService.getTemplateMsgService().sendTemplateMsg(message);
//            LOG.info("Send template message successfully, to: {}", message.getToUser());
//            LOG.info("Template message: {}", message.toJson());
//        } catch (WxErrorException ex) {
//            LOG.error("Send template message error: {}, {}", message.toJson(), ex.fillInStackTrace());
//        }
//    }
//
//    protected String getCallbackUrl(String url) {
//        return String.format(CALLBACK_USERINFO_URL,
//                             wechatMpProperties.getAppId(),
//                             wechatMpProperties.getWxRedirectDomain() + "/wechat" + url);
//    }
//
//    protected XWxToken getToken(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        LOG.debug("arg0: {}", args.length > 0 ? args[0] : "0 empty");
//        LOG.debug("arg1: {}", args.length > 1 ? args[1] : "1 empty");
//        if (args.length > 0 && StringUtils.isNotEmpty((String) args[0])) {
//            return (XWxToken) authenticateTokenHandler.parseToken((String) args[0]);
//        }
//        if (args.length > 1 && StringUtils.isNotEmpty((String) args[1])) {
//            return (XWxToken) authenticateTokenHandler.parseToken((String) args[1]);
//        }
//        return null;
//    }
//}
