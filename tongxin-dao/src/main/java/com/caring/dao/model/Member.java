package com.caring.dao.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "member")
public class Member extends  BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10, sequenceName = "member_id_seq", name = "member_id_seq")
    @GeneratedValue(generator = "member_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "is_forbid")
    private Boolean forbid;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image headImage;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @OrderBy("id")
    @JoinTable(
            name = "member_address",
            joinColumns = {
                    @JoinColumn(name = "member_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "address_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Address> addresses;      //尺寸

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Image getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Image headImage) {
        this.headImage = headImage;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Boolean getForbid() {        return forbid;
    }

    public void setForbid(Boolean forbid) {
        this.forbid = forbid;
    }
}
