//package com.caring.dao.model.system;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// *
// * @author james
// */
//@Entity
//@Table(name = "system_options")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
//public class SystemOption implements Serializable {
//
//    @Id
//    @Column(name = "option_key")
//    private String optionKey;
//    @Column(name = "option_name_cn")
//    private String optionNameCn;
//    @Column(name = "option_name_en")
//    private String optionNameEn;
//    @Column(name = "option_group_key")
//    private String optionGroupKey;
//    @Column(name = "option_value_str")
//    private String optionValueStr;
//    @Column(name = "option_value_int")
//    private Integer optionValueInt;
//    @Column(name = "option_value_float")
//    private Float optionValueFloat;
//
//    public String getOptionKey() {
//        return optionKey;
//    }
//
//    public void setOptionKey(String optionKey) {
//        this.optionKey = optionKey;
//    }
//
//    public String getOptionNameCn() {
//        return optionNameCn;
//    }
//
//    public void setOptionNameCn(String optionNameCn) {
//        this.optionNameCn = optionNameCn;
//    }
//
//    public String getOptionNameEn() {
//        return optionNameEn;
//    }
//
//    public void setOptionNameEn(String optionNameEn) {
//        this.optionNameEn = optionNameEn;
//    }
//
//    public String getOptionGroupKey() {
//        return optionGroupKey;
//    }
//
//    public void setOptionGroupKey(String optionGroupKey) {
//        this.optionGroupKey = optionGroupKey;
//    }
//
//    public String getOptionValueStr() {
//        return optionValueStr;
//    }
//
//    public void setOptionValueStr(String optionValueStr) {
//        this.optionValueStr = optionValueStr;
//    }
//
//    public Integer getOptionValueInt() {
//        return optionValueInt;
//    }
//
//    public void setOptionValueInt(Integer optionValueInt) {
//        this.optionValueInt = optionValueInt;
//    }
//
//    public Float getOptionValueFloat() {
//        return optionValueFloat;
//    }
//
//    public void setOptionValueFloat(Float optionValueFloat) {
//        this.optionValueFloat = optionValueFloat;
//    }
//
//}
