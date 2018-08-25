//package com.caring.service.wx.builder.template.service;
//
//import static com.caring.dao.config.ModelConst.User.CustomerData.FOLLOWUP;
//import static com.caring.dao.config.ModelConst.User.CustomerData.HISTORY;
//import static com.caring.dao.config.ModelConst.User.Inquiry.ILLNESS_REQUIRED;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_REJECT;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_MESSAGE;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_RESERVATION;
//import static com.caring.dao.config.ModelConst.User.Inquiry.INQUIRY_TYPE_SUBSEQUENT;
//import com.caring.dao.model.Answer;
//import com.caring.dao.model.BaseEntity;
//import com.caring.dao.model.Customer;
//import com.caring.dao.model.Followup;
//import com.caring.dao.model.History;
//import com.caring.dao.model.Inquiry;
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.builder.template.WxTemplateMessageService.WxTemplateDataBuilder;
//import static com.caring.service.wx.builder.template.WxTemplateMessageService.create;
//import static com.caring.service.wx.builder.template.WxTemplateMessageService.throwInvalidArgumentObjectException;
//import com.google.common.collect.ImmutableMap;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
//import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// *
// * @author james
// */
//public class WxXianxinTemplateMessageService {
//
//    protected final static Logger LOG = LoggerFactory.getLogger(WxXianxinTemplateMessageService.class);
//
//    public static class InquiryReservationSubmitTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 客服将电话问诊预约流转到医生，提醒小组创建人角色
//            // {{first.DATA}}
//            // 预约姓名：{{keyword1.DATA}}
//            // 预约电话：{{keyword2.DATA}}
//            // 预约时间：{{keyword3.DATA}}
//            // 预约项目：{{keyword4.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 你有一个患者电话问诊预约提醒
//            // 预约姓名：张三
//            // 预约电话：13111111111
//            // 预约时间：2014年7月21日 18:36
//            // 预约项目：电话问诊预约
//            // 点击处理患者的预约
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你有一个患者电话问诊预约提醒", "#57bfe9"),
//                                                            create("keyword1", inquiry.getCustomer().getName()),
//                                                            create("keyword2", inquiry.getQuestion().getMobile()),
//                                                            create("keyword3", sdf.format(inquiry.getCreated())),
//                                                            create("keyword4", "电话问诊预约"),
//                                                            create("remark", "点击处理患者的预约", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("zcB2JzOIr7sQdWcxq9WdmUrc_9X2eXOBvCe_fw2SeqE")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquiryMessageSubmitTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 客服将咨询流转到医生，提醒小组创建人角色
//            // {{first.DATA}}
//            // 用户名称：{{keyword1.DATA}}
//            // 咨询内容：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 你好，你有一条患者咨询待解决
//            // 用户名称：王鹏 王子轩
//            // 咨询内容：您好术后伤疤怎么护理？
//            // 点击处理咨询
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，你有一条患者咨询待解决", "#57bfe9"),
//                                                            create("keyword1", inquiry.getCustomer().getName() + " " + inquiry.getCustomer().getChild().getName()),
//                                                            create("keyword2", inquiry.getQuestion().getDescription()),
//                                                            create("keyword3", sdf.format(inquiry.getCreated())),
//                                                            create("remark", "点击处理咨询", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("9C6TqR9xW8nsNlRyGsf6xp2emDKYjJ6NqxfY2UoPgWA")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquirySubSequentSubmitTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 客服将复诊预约流转到医生，提醒小组创建人角色
//            // {{first.DATA}}
//            // 预约项目：{{keyword1.DATA}}
//            // 就诊姓名：{{keyword2.DATA}}
//            // 病情描述：{{keyword3.DATA}}
//            // 可约时段：{{keyword4.DATA}}
//            // 申请时间：{{keyword5.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 你有一个复诊预约申请提醒。
//            // 预约项目：复诊预约
//            // 就诊姓名：王文灵
//            // 病情描述：头痛，反馈，睡不醒，已经没号了希望加号
//            // 可约时段：null
//            // 申请时间：2016年9月21日 18:36
//            // 点击处理患者的预约。
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你有一个复诊预约申请提醒", "#57bfe9"),
//                                                            create("keyword1", "复诊预约"),
//                                                            create("keyword2", inquiry.getCustomer().getName()),
//                                                            create("keyword3", inquiry.getQuestion().getDescription()),
//                                                            create("keyword5", sdf.format(inquiry.getCreated())),
//                                                            create("remark", "点击处理患者的预约", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("-L-a6Fsrrcrm5rK5ckDCWBeKnefwnYK3Bkb6NfTdfNE")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquiryReservationScheduledTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者收到医生的电话问诊预约安排提醒
//            // {{first.DATA}}
//            // 预约内容：{{keyword1.DATA}}
//            // 预约时间：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 你好，你的电话问诊预约已经预约成功
//            // 预约内容：电话问诊预约
//            // 预约时间：2016-5-02 19:00
//            // 你的接听电话时间可能会提前或滞后半小时，接听电话号码为4006771997
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，你的电话问诊预约已经预约成功", "#57bfe9"),
//                                                            create("keyword1", "电话问诊预约"),
//                                                            create("keyword2", sdf.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("remark", "你的接听电话时间可能会提前或滞后半小时，接听电话号码为4006771997", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("Z_UEvxoRRZk1JYrhjkXfVKEMfSxEoDq0I28fyVfslsY")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquirySubSequentScheduledTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者收到医生的复诊安排提醒
//            // {{first.DATA}}
//            // 医生姓名：{{keyword1.DATA}}
//            // 预约时间：{{keyword2.DATA}}
//            // 预约地点：{{keyword3.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 你好，根据你的申请，医生为你安排了复诊预约。
//            // 医生姓名：李时珍
//            // 预约时间：2016年07月05日 10:00 - 12:00
//            // 预约地点：广东省人民医院心胸外科
//            // 请准时根据医生安排就诊，直接到诊室找医生沟通复诊事宜，携带到以往检查报告。"
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                Answer answer = inquiry.getAnswer();
//                String doctorName = "", keyword3 = "";
//                if (answer != null && answer.getDoctor() != null) {
//                    doctorName = answer.getDoctor().getName();
//                }
//                if (answer != null && answer.getHospital() != null) {
//                    keyword3 += answer.getHospital().getName();
//                }
//                if (answer != null && answer.getOffice() != null) {
//                    keyword3 += StringUtils.isNotEmpty(keyword3) ? ("," + answer.getOffice().getName()) : answer.getOffice().getName();
//                }
//                if (answer != null && StringUtils.isNotEmpty(answer.getDoctorName())) {
//                    doctorName = answer.getDoctorName();
//                }
//                if (answer != null && StringUtils.isNotEmpty(answer.getHospitalName())) {
//                    keyword3 += answer.getHospitalName();
//                }
//                if (answer != null && StringUtils.isNotEmpty(answer.getOfficeName())) {
//                    keyword3 += StringUtils.isNotEmpty(keyword3) ? ("," + answer.getOfficeName()) : answer.getOfficeName();
//                }
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，根据你的申请，医生为你安排了复诊预约", "#57bfe9"),
//                                                            create("keyword1", doctorName),
//                                                            create("keyword2", sdf.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("keyword3", keyword3),
//                                                            create("remark", "请准时根据医生安排就诊，直接到诊室找医生沟通复诊事宜，携带以往检查报告", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("ElUzrU_xOPKSeSw6tlKoCftJwvGWBElRF4vI0I0j1XE")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquiryMessageClosedTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者收到医生的留言回复提醒
//            // {{first.DATA}}
//            // 你的提问：{{keyword1.DATA}}
//            // 医生的回复：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 医生回复了您的留言问诊
//            // 你的提问：关于小儿骨骼发育问题
//            // 医生的回复：xxxxxxx
//            // 点击查看医生回复详情"
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "医生回复了您的留言问诊", "#57bfe9"),
//                                                            create("keyword1", inquiry.getQuestion().getDescription()),
//                                                            create("keyword2", inquiry.getAnswer().getAnswer()),
//                                                            create("remark", "点击查看医生回复详情", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("m3r_wbx3fO96y2mRRdHG4IU47Wnwzkivzd1HW9wTZas")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class InquiryMessageRejectTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者收到医生的留言回复提醒
//            // {{first.DATA}}
//            // 你的提问：{{keyword1.DATA}}
//            // 医生的回复：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 医生回复了您的留言问诊
//            // 你的提问：关于小儿骨骼发育问题
//            // 医生的回复：xxxxxxx
//            // 点击查看医生回复详情"
//            // ----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                String typeName = "留言问诊";
//                if (INQUIRY_TYPE_RESERVATION.equals(inquiry.getType())) {
//                    typeName = "电话预约";
//                } else if (INQUIRY_TYPE_SUBSEQUENT.equals(inquiry.getType())) {
//                    typeName = "留言复诊";
//                }
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "医生回复了您的" + typeName, "#57bfe9"),
//                                                            create("keyword1", inquiry.getQuestion().getDescription()),
//                                                            create("keyword2", inquiry.getRejectReason()),
//                                                            create("remark", "点击查看医生回复详情", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("m3r_wbx3fO96y2mRRdHG4IU47Wnwzkivzd1HW9wTZas")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class FollowupSubmitTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者填写随访表单提交后，提醒整个小组的所有医生
//            // {{first.DATA}}
//            // 提交表单：{{keyword1.DATA}}
//            // 提交时间：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 患者刚刚提交了随访表
//            // 提交表单：患者随访表
//            // 提交时间：2014年7月21日 18:36
//            // 点击查看随访表详情
//            // ----------------------------
//            if (entity instanceof Followup) {
//                Followup followup = (Followup) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "患者刚刚提交了随访表", "#57bfe9"),
//                                                            create("keyword1", "患者随访表"),
//                                                            create("keyword2", sdf.format(followup.getCreated())),
//                                                            create("remark", "点击查看随访表详情", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("2rCyTMUSbBMLWRQhXqfI0UyrjBPbeCmsO_95Ui8IMGo")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class HistorySubmitTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 患者上传病例报告提醒整个小组的所有医生
//            // {{first.DATA}}
//            // 提交表单：{{keyword1.DATA}}
//            // 提交时间：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 患者刚刚上传了病历报告
//            // 提交表单：患者病例资料
//            // 提交时间：2014年7月21日 18:36
//            // 点击查看随访表详情
//            // ----------------------------
//            if (entity instanceof History) {
//                History history = (History) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "患者刚刚上传了病历报告", "#57bfe9"),
//                                                            create("keyword1", "患者病历资料"),
//                                                            create("keyword2", sdf.format(history.getCreated())),
//                                                            create("remark", "点击查看病历报告详情", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("2rCyTMUSbBMLWRQhXqfI0UyrjBPbeCmsO_95Ui8IMGo")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    public static class IllnessRequiredTemplBuilder implements WxTemplateDataBuilder {
//
//        @Override
//        public WxMpTemplateMessage build(BaseEntity entity) {
//            // 用户注册后填写基础病情表单提醒
//            // {{first.DATA}}
//            // 随访类型：{{keyword1.DATA}}
//            // 参与时间：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            // 注册成功！必填通知：填写此表单才能享受医生的随访服务
//            // 随访类型：诊后随访
//            // 参与时间：2016年3月31日
//            // 点击立即填写病情表单，享受医生随访服务
//            // ----------------------------
//            if (entity instanceof Customer) {
//                Customer customer = (Customer) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "注册成功！必填通知：填写此表单才能享受医生的随访服务", "#57bfe9"),
//                                                            create("keyword1", "诊后随访"),
//                                                            create("keyword2", sdf.format(customer.getCreated())),
//                                                            create("remark", "点击立即填写病情表单，享受医生随访服务", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(customer.getWechat().getOpenId())
//                        .templateId("Bq0BFd45SXsHQ5K5CVtOCmX_PROI3zkZA5Uh72hr_dY")
//                        .data(data).build();
//            }
//            return throwInvalidArgumentObjectException(entity);
//        }
//    }
//
//    // message for reminder
//    private final Map<String, WxTemplateDataBuilder> MESSAGE_SUBMIT_LIST = ImmutableMap.<String, WxTemplateDataBuilder>builder()
//            .put(INQUIRY_TYPE_RESERVATION, new InquiryReservationSubmitTemplBuilder())
//            .put(INQUIRY_TYPE_MESSAGE, new InquiryMessageSubmitTemplBuilder())
//            .put(INQUIRY_TYPE_SUBSEQUENT, new InquirySubSequentSubmitTemplBuilder())
//            .put(ILLNESS_REQUIRED, new IllnessRequiredTemplBuilder())
//            .put(FOLLOWUP, new FollowupSubmitTemplBuilder())
//            .put(HISTORY, new HistorySubmitTemplBuilder())
//            .build();
//
//    private final Map<String, WxTemplateDataBuilder> MESSAGE_CLOSED_LIST = ImmutableMap.<String, WxTemplateDataBuilder>builder()
//            .put(INQUIRY_TYPE_RESERVATION, new InquiryReservationScheduledTemplBuilder())
//            .put(INQUIRY_TYPE_SUBSEQUENT, new InquirySubSequentScheduledTemplBuilder())
//            .put(INQUIRY_TYPE_MESSAGE, new InquiryMessageClosedTemplBuilder())
//            .put(INQUIRY_REJECT, new InquiryMessageRejectTemplBuilder())
//            .build();
//
//    public WxTemplateDataBuilder getSubmitMessageBuilder(String messageType) {
//        return MESSAGE_SUBMIT_LIST.get(messageType);
//    }
//
//    public WxTemplateDataBuilder getClosedMessageBuilder(String messageType) {
//        return MESSAGE_CLOSED_LIST.get(messageType);
//    }
//}
