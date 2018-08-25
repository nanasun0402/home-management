//package com.caring.service.wx.builder.template;
//
//import com.caring.dao.model.BaseEntity;
//import static com.caring.service.CaringServiceConst.DOMAIN_SUIFANG;
//import static com.caring.service.CaringServiceConst.WECHAT_DOMAIN;
//import com.caring.service.CaringServiceException;
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.builder.template.service.WxSuiFangTemplateMessageService;
//import com.caring.service.wx.builder.template.service.WxXianxinTemplateMessageService;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author james
// */
//@Service
//public class WxTemplateMessageService {
//
//    protected final static Logger LOG = LoggerFactory.getLogger(WxTemplateMessageService.class);
//
//    public static interface WxTemplateDataBuilder {
//
//        WxMpTemplateMessage build(BaseEntity entity);
//    }
//
//    @Autowired
//    private Environment env;
//
//    private final WxSuiFangTemplateMessageService wxSuiFangTemplateMessageService = new WxSuiFangTemplateMessageService();
//    private final WxXianxinTemplateMessageService wxXianxinTemplateMessageService = new WxXianxinTemplateMessageService();
//
//    public static WxMpTemplateData create(String keyword, String value) {
//        return new WxMpTemplateData(keyword, value);
//    }
//
//    public static WxMpTemplateData create(String keyword, String value, String color) {
//        return new WxMpTemplateData(keyword, value, color);
//    }
//
//    public static WxMpTemplateMessage throwInvalidArgumentObjectException(BaseEntity entity) {
//        LOG.error("Invalid argument object type: {}", ServiceUtils.toJson(entity));
//        throw new CaringServiceException("参数类型错误, 不合法的咨询对象");
//    }
//
//    public WxTemplateDataBuilder getSubmitMessageBuilder(String messageType) {
//        if (StringUtils.equalsIgnoreCase(DOMAIN_SUIFANG, env.getProperty(WECHAT_DOMAIN))) {
//            return wxSuiFangTemplateMessageService.getSubmitMessageBuilder(messageType);
//        }
//        return wxXianxinTemplateMessageService.getSubmitMessageBuilder(messageType);
//    }
//
//    public WxTemplateDataBuilder getClosedMessageBuilder(String messageType) {
//        if (StringUtils.equalsIgnoreCase(DOMAIN_SUIFANG, env.getProperty(WECHAT_DOMAIN))) {
//            return wxSuiFangTemplateMessageService.getClosedMessageBuilder(messageType);
//        }
//        return wxXianxinTemplateMessageService.getClosedMessageBuilder(messageType);
//    }
//}
