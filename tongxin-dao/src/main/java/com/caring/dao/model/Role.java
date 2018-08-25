//package com.caring.dao.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import org.hibernate.annotations.DynamicUpdate;
//import org.springframework.security.core.GrantedAuthority;
//
///**
// *
// * @author james
// */
//@Entity
//@DynamicUpdate
//@Table(name = "role_info")
//public class Role extends BaseEntity implements GrantedAuthority {
//
//    @Id
//    @Column(name = "authority_key")
//    private String key;
//
//    @Column(name = "label")
//    private String label;
//
//    @Column(name = "description", length = 2048)
//    private String description;
//
//    @Column(name = "type")
//    private String type;
//
//    public Role(String roleKey) {
//        this.key = roleKey;
//    }
//
//    public Role() {
//
//    }
//
//    @Override
//    public String getAuthority() {
//        return this.key;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//}
