package com.caring.service.wx.handler;

import java.util.Map;
import me.chanjar.weixin.common.api.WxConsts;

import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            if (WxConsts.XML_MSG_TEXT.equals(wxMessage.getMsgType())
                    || WxConsts.XML_MSG_IMAGE.equals((wxMessage.getMsgType()))) {
                return null;
            } else {
                return super.buildTextOutMessage("不支持的格式", wxMessage);
            }
        } catch (Exception e) {
            return WxMpXmlOutMessage.TEXT().content(e.getLocalizedMessage()).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
        }
    }

}
