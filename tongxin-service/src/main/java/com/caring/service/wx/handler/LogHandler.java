package com.caring.service.wx.handler;

import java.util.Map;
import org.springframework.stereotype.Component;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component
public class LogHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        // LOG.info(String.format("\n接收到请求消息，内容：%s", ServiceUtils.toJson(wxMessage)));
        return null;
    }

}
