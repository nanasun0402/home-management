//package com.caring.dao.model;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.OneToOne;
//import javax.persistence.SequenceGenerator;
//
///**
// *
// * @author james
// */
//@MappedSuperclass
//public class BaseUser extends BaseEntity {
//
//    @Id
//    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "user_id_seq", name = "user_id_seq")
//    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "mobile")
//    private String mobile;
//
//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "description")
//    private String description;
//
//    @OneToOne
//    @JoinColumn(name = "image_id")
//    private Image headImage;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "openid")
//    private Wechat wechat;
//
//    public BaseUser() {
//    }
//
//    public BaseUser(Long id, String name, Image headImage) {
//        this.id = id;
//        this.name = name;
//        this.headImage = headImage;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Image getHeadImage() {
//        return headImage;
//    }
//
//    public void setHeadImage(Image headImage) {
//        this.headImage = headImage;
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
//    public Wechat getWechat() {
//        return wechat;
//    }
//
//    public void setWechat(Wechat wechat) {
//        this.wechat = wechat;
//    }
//}
