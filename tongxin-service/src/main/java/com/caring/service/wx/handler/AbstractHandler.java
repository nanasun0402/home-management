package com.caring.service.wx.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHandler implements WxMpMessageHandler {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected WxMpXmlOutMessage buildTextOutMessage(String text, WxMpXmlMessage wxMessage) {
        return WxMpXmlOutMessage.TEXT().content(text).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
    }
}
