//package com.caring.dao.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import org.hibernate.annotations.DynamicUpdate;
//
///**
// *
// * @author james
// */
//@Entity
//@DynamicUpdate
//@Table(name = "verify_code")
//public class VerifyCode extends BaseEntity {
//
//    @Id
//    private String mobile;
//
//    @Column(name = "code")
//    private String code;
//
//    @Column(name = "generated_time")
//    private Long generatedTime;
//
//    @Column(name = "send_status")
//    private Integer sendStatus;
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public Long getGeneratedTime() {
//        return generatedTime;
//    }
//
//    public void setGeneratedTime(Long generatedTime) {
//        this.generatedTime = generatedTime;
//    }
//
//    public Integer getSendStatus() {
//        return sendStatus;
//    }
//
//    public void setSendStatus(Integer sendStatus) {
//        this.sendStatus = sendStatus;
//    }
//
//}
