//package com.caring.service.wx.handler;
//
//import com.caring.dao.model.BindingCache;
//import com.caring.dao.model.Doctor;
//import com.caring.dao.model.Wechat;
//import com.caring.dao.service.CustomerService;
//import com.caring.dao.service.DoctorService;
//import com.caring.dao.service.WechatService;
//import com.caring.service.CaringServiceConst;
//import static com.caring.service.CaringServiceConst.DOMAIN_SUIFANG;
//import static com.caring.service.CaringServiceConst.MOCK_DOCTOR_TOKEN;
//import static com.caring.service.CaringServiceConst.WECHAT_DOMAIN;
//import com.caring.service.CaringServiceException;
//import com.caring.service.wx.builder.tag.WxTagHandler;
//import static com.caring.service.wx.builder.tag.WxTagHandler.CUSTOMER_TAG;
//import static com.caring.service.wx.builder.tag.WxTagHandler.DOCTOR_TAG;
//import java.util.Arrays;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import me.chanjar.weixin.mp.bean.result.WxMpUser;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//
///**
// *
// * @author james
// */
//public abstract class AbstractUserBindHandler extends AbstractHandler {
//
//    @Autowired
//    private WechatService wechatService;
//    @Autowired
//    private DoctorService doctorService;
//    @Autowired
//    private CustomerService customerService;
//    @Autowired
//    private Environment env;
//
//    protected WxMpXmlOutMessage subcribe(WxMpService wxMpService, WxMpUser user, WxMpXmlMessage wxMessage) {
//        Wechat wechat = new Wechat();
//        wechat.setOpenId(user.getOpenId());
//        wechat.setCountry(user.getCountry());
//        wechat.setGroupId(user.getGroupId());
//        wechat.setHeadImgUrl(user.getHeadImgUrl());
//        wechat.setLang(user.getLanguage());
//        wechat.setNickname(user.getNickname());
//        wechat.setProvince(user.getProvince());
//        wechat.setRemark(user.getRemark());
//        wechat.setSex(user.getSex());
//        wechat.setSubscribe(user.getSubscribe());
//        wechat.setSubscribeTime(user.getSubscribeTime());
//        if (user.getTagIds() != null) {
//            wechat.setTagIds(Arrays.toString(user.getTagIds()));
//        }
//        wechatService.saveWechat(wechat);
//        updateTagIfReSubscribe(wxMpService, wechat);
//        WxMpXmlOutMessage message = scanDoctorQrCode(wxMpService, user, wxMessage);
//        BindingCache binding = wechatService.findBindingByOpenId(user.getOpenId());
//        if (binding == null || binding.getDoctor() == null) {
//            //return buildTextOutMessage("您没有随访医生不能注册，请先联系随访医助", wxMessage);
//        }
//        return message != null ? message : buildTextOutMessage("欢迎关注我们的医助服务平台", wxMessage);
//    }
//
//    protected void unSubscribe(WxMpUser user) {
//        Wechat wechat = new Wechat();
//        wechat.setOpenId(user.getOpenId());
//        wechat.setSubscribe(false);
//        wechatService.saveWechat(wechat);
//    }
//
//    protected void updateTagIfReSubscribe(WxMpService wxMpService, Wechat wechat) {
//        if (wechat.getOpenId().contains(MOCK_DOCTOR_TOKEN)) {
//            LOG.info("Mocking openid, the tag will not be updated");
//            return;
//        }
//        WxTagHandler wxTagHandler = new WxTagHandler(wxMpService);
//        if (doctorService.findDoctorByOpenId(wechat.getOpenId()) != null) {
//            wxTagHandler.tagging(DOCTOR_TAG, wechat.getOpenId());
//        } else if (customerService.findCustomerByOpenId(wechat.getOpenId()) != null) {
//            wxTagHandler.tagging(CUSTOMER_TAG, wechat.getOpenId());
//        }
//    }
//
//    protected WxMpXmlOutMessage scanDoctorQrCode(WxMpService wxMpService, WxMpUser user, WxMpXmlMessage wxMessage) {
//        try {
//            Doctor doctor = extractDoctorFromEvent(wxMessage);
//            if (doctor == null) {
//                return buildTextOutMessage(getReplyText(), wxMessage);
//            }
//            BindingCache binding = wechatService.findBindingByOpenId(user.getOpenId());
//            if (binding != null && binding.getDoctor() != null && !doctor.getId().equals(binding.getDoctor().getId())) {
//                throw new CaringServiceException("您不能绑定不同的医生二维码");
//            }
//            binding = new BindingCache();
//            binding.setOpenId(user.getOpenId());
//            binding.setDoctor(doctor);
//            wechatService.saveBindingCache(binding);
//        } catch (CaringServiceException e) {
//            return buildTextOutMessage(e.getMessage(), wxMessage);
//        }
//        return buildTextOutMessage(getReplyTextWithUrl(wxMpService), wxMessage);
//    }
//
//    private String getReplyText() {
//        if (StringUtils.equalsIgnoreCase(DOMAIN_SUIFANG, env.getProperty(WECHAT_DOMAIN))) {
//            return new StringBuilder().append("欢迎来到我科室专业随访平台").append("\n\n")
//                    .append("为了您和您家人的健康，请积极配合随访医助上传病历报告和随访表，让医生及")
//                    .append("时掌握您的身体情况，以便我们医生团队对您进行专业及时的康复指导").toString();
//        }
//        return "欢迎关注“中国心脏出生缺陷一体化诊疗协作组”微信平台，我们将在更大范围内真正解决好胎儿"
//                + "心脏出生缺陷的诊断和临床评估难题，杜绝盲目流产，减少对孕妇心理与生理健康造成的损害。";
//    }
//
//    private String getReplyTextWithUrl(WxMpService wxMpService) {
//        String appId = wxMpService.getWxMpConfigStorage().getAppId();
//        if (StringUtils.equalsIgnoreCase(DOMAIN_SUIFANG, env.getProperty(WECHAT_DOMAIN))) {
//            return new StringBuilder().append("欢迎来到我科室专业随访平台").append("\n\n")
//                    .append("为了您和您家人的健康，")
//                    .append("<a href=\"https://mp.weixin.qq.com/s/KZ6WZvgc-q4BHVptmTQegA\">")
//                    .append("请积极配合随访医助</a>")
//                    .append("上传病历报告和随访表，让医生及")
//                    .append("时掌握您的身体情况，以便我们医生团队对您进行专业及时的康复指导").append("\n\n")
//                    .append("<a href=\"http://mp.weixin.qq.com/s?__biz=MzI1MzEyNTUwNw==&mid=503418575&idx=1&sn=fe47f7d3e57e09a6f3c8eb0583a1dcea&chksm=722c1823455b91350813ebdb1a3bd8a3a23e6a41b6121b34a5194f76ef185779b8a0ada4692a#rd\">")
//                    .append("查看注册流程，点这里 >> </a>").append("\n\n")
//                    .append("<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
//                    .append(appId)
//                    .append("&redirect_uri=http://suifang.1xiangsui.com/wechat/user/home&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect\">")
//                    .append("点此注册，加入随访小组 >> </a>").toString();
//        }
//        return getReplyText() + "\n\n"
//                + "<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="
//                + appId
//                + "&redirect_uri=http://wechat.1xiangsui.com/wechat/user/home&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect\">"
//                + "进入我的小组，点这里 >> </a>\n\n"
//                + "<a href=\"http://mp.weixin.qq.com/s?__biz=MzI5NTg5MTIzNw==&mid=100000014&idx=1&sn=bbda89e6cd07ee00089f09ad3b0e8f27&chksm=6c4df9445b3a7052188fea5019f98f369656d3587daad224d497ad082146338057033674fa9a#rd\">"
//                + "找医助导诊， 点这里 >> </a>\n";
//    }
//
//    private Doctor extractDoctorFromEvent(WxMpXmlMessage wxMessage) {
//        // Extract doctor ID that is in QRCode
//        String doctorIdStr = null;
//        if (!wxMessage.getEventKey().contains(CaringServiceConst.QRCODE_DOCTORID_PREFIX)
//                && !wxMessage.getEventKey().contains(CaringServiceConst.DOCTORID_PREFIX)) {
//            return null;
//        }
//        if (wxMessage.getEventKey().startsWith(CaringServiceConst.QRCODE_DOCTORID_PREFIX)) {
//            LOG.debug("Scan and subscribe user");
//            doctorIdStr = wxMessage.getEventKey();
//            doctorIdStr = doctorIdStr.substring(CaringServiceConst.QRCODE_DOCTORID_PREFIX.length());
//        } else if (wxMessage.getEventKey().startsWith(CaringServiceConst.DOCTORID_PREFIX)) {
//            LOG.debug("Scan user");
//            doctorIdStr = wxMessage.getEventKey();
//            doctorIdStr = doctorIdStr.substring(CaringServiceConst.DOCTORID_PREFIX.length());
//        } else {
//            throw new CaringServiceException("无效的医生二维码");
//        }
//        LOG.debug("Find doctor id: {}", doctorIdStr);
//        Long doctorId = Long.parseLong(doctorIdStr);
//        Doctor doctor = doctorService.findDoctorById(doctorId);
//        if (doctor == null) {
//            LOG.error("Wrong doctorId: {}", doctorId);
//            throw new CaringServiceException("无效的医生二维码");
//        }
//        return doctor;
//    }
//}
