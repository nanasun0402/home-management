//package com.caring.service.wx.handler;
//
//import com.caring.service.wx.builder.TextBuilder;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import me.chanjar.weixin.common.exception.WxErrorException;
//import me.chanjar.weixin.common.session.WxSessionManager;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import me.chanjar.weixin.mp.bean.result.WxMpUser;
//
//@Component
//public class SubscribeHandler extends AbstractUserBindHandler {
//
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
//                                    Map<String, Object> context, WxMpService wxMpService,
//                                    WxSessionManager sessionManager) throws WxErrorException {
//        try {
//            LOG.info("新关注用户 OPENID: " + wxMessage.getFromUser());
//            WxMpUser wxUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
//            return subcribe(wxMpService, wxUser, wxMessage);
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//        }
//
//        try {
//            return new TextBuilder().build("感谢关注童心伴侣", wxMessage, wxMpService);
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//        }
//
//        return null;
//    }
//}
