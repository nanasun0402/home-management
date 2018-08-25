//package com.caring.service.wx.handler;
//
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import me.chanjar.weixin.common.session.WxSessionManager;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import me.chanjar.weixin.mp.bean.result.WxMpUser;
//
//@Component
//public class UnsubscribeHandler extends AbstractUserBindHandler {
//
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
//                                    Map<String, Object> context, WxMpService wxMpService,
//                                    WxSessionManager sessionManager) {
//
//        try {
//            this.LOG.info("取消关注用户 OPENID: " + wxMessage.getFromUser());
//            WxMpUser wxUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
//            unSubscribe(wxUser);
//        } catch (Exception e) {
//            this.LOG.error(e.getMessage(), e);
//        }
//        return null;
//    }
//
//}
