package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "address")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Address extends  BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "address_id_seq", name = "address_id_seq")
    @GeneratedValue(generator = "address_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;//收货人姓名

    @Column(name="mobile")
    private String mobile;//收货人电话

    @Column(name="city")
    private String city;//收货人地址

    @Column(name="address")
    private String address;//收货人地址

    @Column(name="is_default")
    private String isDefault;// 默认地址

    @Column(name="post_code")
    private String postCode;//邮编

    @OneToOne
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
