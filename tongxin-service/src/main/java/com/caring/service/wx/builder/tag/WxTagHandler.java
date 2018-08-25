//package com.caring.service.wx.builder.tag;
//
//import com.caring.service.CaringServiceException;
//import java.util.List;
//import me.chanjar.weixin.common.exception.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.tag.WxUserTag;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author james
// */
//@Service
//public class WxTagHandler {
//
//    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
//
//    public final static String CUSTOMER_TAG = "患者";
//    public final static String DOCTOR_TAG = "医生";
//
//    @Autowired
//    private WxMpService wxService;
//
//    public WxTagHandler(WxMpService wxService) {
//        this.wxService = wxService;
//    }
//
//    public WxTagHandler() {
//    }
//
//    public Long tagGet(String tagName) {
//        try {
//            List<WxUserTag> tags = wxService.getUserTagService().tagGet();
//            for (WxUserTag tag : tags) {
//                LOG.info("{}:{}:{}", tag.getId(), tag.getName(), tag.toJson());
//                if (StringUtils.equals(tagName, tag.getName())) {
//                    return tag.getId();
//                }
//            }
//            return null;
//        } catch (WxErrorException ex) {
//            LOG.error("Error: {}", ex.fillInStackTrace());
//            throw new CaringServiceException("微信标签读取错误");
//        }
//    }
//
//    public Long tagging(String tagName, String openId) {
//        try {
//            Long tagId = this.tagGet(tagName);
//            if (tagId != null) {
//                wxService.getUserTagService().batchTagging(tagId, new String[]{openId});
//            }
//            return tagId;
//        } catch (WxErrorException ex) {
//            LOG.error("Error: {}", ex.fillInStackTrace());
//            throw new CaringServiceException("微信标签更新错误");
//        }
//    }
//
//    public void batchTagging(Long tagId, String[] openIds) {
//        try {
//            if (tagId != null) {
//                wxService.getUserTagService().batchTagging(tagId, openIds);
//            }
//        } catch (WxErrorException ex) {
//            LOG.error("Error: {}", ex.fillInStackTrace());
//            throw new CaringServiceException("微信标签更新错误");
//        }
//    }
//}
