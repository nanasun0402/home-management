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
//public class WxSuiFangTemplateMessageService {
//
//    protected final static Logger LOG = LoggerFactory.getLogger(WxSuiFangTemplateMessageService.class);
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
//            // {{first.DATA}}
//            // 用户名称：{{keyword1.DATA}}
//            // 咨询内容：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                String content = new StringBuilder()
//                        .append("联系电话：").append(inquiry.getQuestion().getMobile())
//                        .append(", 预约时间：").append(sdf.format(inquiry.getCreated())).toString();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你有一个患者电话问诊预约提醒", "#57bfe9"),
//                                                            create("keyword1", inquiry.getCustomer().getName()),
//                                                            create("keyword2", content),
//                                                            create("remark", "点击处理患者的预约", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("qLwhpwNqtaTPMHXsXcfhul94igaAT02X8_hLuNXlcQ8")
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
//            // ----------------------------
//            // {{first.DATA}}
//            // 用户名称：{{keyword1.DATA}}
//            // 咨询内容：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，你有一条患者咨询待解决", "#57bfe9"),
//                                                            create("keyword1", inquiry.getCustomer().getName() + " " + inquiry.getCustomer().getChild().getName()),
//                                                            create("keyword2", inquiry.getQuestion().getDescription()),
//                                                            create("remark", "点击处理咨询", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("qLwhpwNqtaTPMHXsXcfhul94igaAT02X8_hLuNXlcQ8")
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
//            // ----------------------------
//            // {{first.DATA}}
//            // 用户名称：{{keyword1.DATA}}
//            // 咨询内容：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            // -----------------------------
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你有一个复诊预约申请提醒", "#57bfe9"),
//                                                            create("keyword1", inquiry.getCustomer().getName()),
//                                                            create("keyword2", inquiry.getQuestion().getDescription()),
//                                                            create("remark", "点击处理患者的预约", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("qLwhpwNqtaTPMHXsXcfhul94igaAT02X8_hLuNXlcQ8")
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
//            // {{first.DATA}}
//            // 预约类型：{{keyword1.DATA}}
//            // 预约日期：{{keyword2.DATA}}
//            // 预约时间：{{keyword3.DATA}}
//            // 咨询人：{{keyword4.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat yyyymmdd = ServiceUtils.getStandardFormatOnyyyyMMdd();
//                SimpleDateFormat hhmm = ServiceUtils.getStandardFormatOnHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，你的电话问诊预约已经预约成功", "#57bfe9"),
//                                                            create("keyword1", "电话问诊预约"),
//                                                            create("keyword2", yyyymmdd.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("keyword3", hhmm.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("remark", "你的接听电话时间可能会提前或滞后半小时，接听电话号码为4006771997", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("Q4SL7dHnR4CNaeCu-wuEoU8-_6_UBooFFb6wWHlbapI")
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
//            // ----------------------------
//            // {{first.DATA}}
//            // 预约类型：{{keyword1.DATA}}
//            // 预约日期：{{keyword2.DATA}}
//            // 预约时间：{{keyword3.DATA}}
//            // 咨询人：{{keyword4.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat yyyymmdd = ServiceUtils.getStandardFormatOnyyyyMMdd();
//                SimpleDateFormat hhmm = ServiceUtils.getStandardFormatOnHHmm();
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
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "你好，根据你的申请，" + doctorName + "医生为你安排了复诊预约, 地点：" + keyword3, "#57bfe9"),
//                                                            create("keyword1", "复诊预约"),
//                                                            create("keyword2", yyyymmdd.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("keyword3", hhmm.format(inquiry.getAnswer().getReceiveTime())),
//                                                            create("keyword4", inquiry.getCustomer().getName()),
//                                                            create("remark", "请准时根据医生安排就诊，直接到诊室找医生沟通复诊事宜，携带以往检查报告", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("Q4SL7dHnR4CNaeCu-wuEoU8-_6_UBooFFb6wWHlbapI")
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
//            // {{first.DATA}}
//            // 咨询内容：{{keyword1.DATA}}
//            // 回复医生：{{keyword2.DATA}}
//            // 回复时间：{{keyword3.DATA}}
//            //{{remark.DATA}}
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "医生回复了您的留言问诊", "#57bfe9"),
//                                                            create("keyword1", inquiry.getQuestion().getDescription()),
//                                                            create("keyword2", inquiry.getGroup().getCreatorDoctorName()),
//                                                            create("keyword3", sdf.format(inquiry.getAnswer().getUpdated())),
//                                                            create("remark", "点击查看医生回复详情", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("ogVwX-UdZL2hz5VlXNaZnZLeUxinwrS8hq_zQsmpfao")
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
//            // 审核不通过通知
//            // {{first.DATA}}
//            // 审核结果：{{keyword1.DATA}}
//            // 原因：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof Inquiry) {
//                Inquiry inquiry = (Inquiry) entity;
//                String typeName = "留言问诊" + "，您的留言：" + inquiry.getQuestion().getDescription();
//                if (INQUIRY_TYPE_RESERVATION.equals(inquiry.getType())) {
//                    typeName = "电话预约";
//                } else if (INQUIRY_TYPE_SUBSEQUENT.equals(inquiry.getType())) {
//                    typeName = "留言复诊" + "，您的留言：" + inquiry.getQuestion().getDescription();
//                }
//                String first = "医生回复了您的" + typeName;
//                List<WxMpTemplateData> data = Arrays.asList(create("first", first, "#57bfe9"),
//                                                            create("keyword1", "拒绝"),
//                                                            create("keyword2", inquiry.getRejectReason()),
//                                                            create("remark", "点击查看医生回复详情", "#dd3f64"));
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(inquiry.getCustomer().getWechat().getOpenId())
//                        .templateId("3fkiH6qEnEy7vkhTDVPgi4U3fRelif1N6LsGy0IxNew")
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
//            // 表单提交通知
//            // {{first.DATA}}
//            // 用户名：{{keyword1.DATA}}
//            // 手机号：{{keyword2.DATA}}
//            // 提交时间：{{keyword3.DATA}}
//            // 内容详情：{{keyword4.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof Followup) {
//                Followup followup = (Followup) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "患者刚刚提交了随访表", "#57bfe9"),
//                                                            create("keyword1", followup.getCustomer().getName()),
//                                                            create("keyword2", followup.getCustomer().getMobile()),
//                                                            create("keyword3", sdf.format(followup.getCreated())),
//                                                            create("keyword4", "患者随访表"),
//                                                            create("remark", "点击查看随访表详情", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("wnNlW4cmqplRhavlp7Pkyq7MFi6j0duVuV2A5MMb3oY")
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
//            // ----------------------------
//            // 表单提交通知
//            // {{first.DATA}}
//            // 用户名：{{keyword1.DATA}}
//            // 手机号：{{keyword2.DATA}}
//            // 提交时间：{{keyword3.DATA}}
//            // 内容详情：{{keyword4.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof History) {
//                History history = (History) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "患者刚刚上传了病历报告", "#57bfe9"),
//                                                            create("keyword1", history.getCustomer().getName()),
//                                                            create("keyword2", history.getCustomer().getMobile()),
//                                                            create("keyword3", sdf.format(history.getCreated())),
//                                                            create("keyword4", "患者病历资料"),
//                                                            create("remark", "点击查看病历报告详情", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .templateId("wnNlW4cmqplRhavlp7Pkyq7MFi6j0duVuV2A5MMb3oY")
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
//            // {{first.DATA}}
//            // 用户名：{{keyword1.DATA}}
//            // 截止日期：{{keyword2.DATA}}
//            // {{remark.DATA}}
//            if (entity instanceof Customer) {
//                Customer customer = (Customer) entity;
//                SimpleDateFormat sdf = ServiceUtils.getStandardFormatOnyyyyMMddHHmm();
//                List<WxMpTemplateData> data = Arrays.asList(create("first", "注册成功！必填通知：填写此表单才能享受医生的随访服务", "#57bfe9"),
//                                                            create("keyword1", customer.getName()),
//                                                            create("keyword2", "请尽快"),
//                                                            create("remark", "点击立即填写病情表单，享受医生随访服务", "#dd3f64"));
//
//                return WxMpTemplateMessage
//                        .builder()
//                        .toUser(customer.getWechat().getOpenId())
//                        .templateId("2Kmor6Z8P097uLs0DxJSTV3-GRPVPP0-csrHkMnDTzI")
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
