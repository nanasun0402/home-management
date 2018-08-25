package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author james
 */
@Entity
@DynamicUpdate
@Table(name = "sysuser")
//extends BaseRoleUser implements UserDetails?
public class User extends  BaseEntity{
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "sysuser_id_seq", name = "sysuser_id_seq")
    @GeneratedValue(generator = "sysuser_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

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

//    @Column(name = "accountexpired", nullable = false)
//    private boolean accountExpired;
//
//    @Column(name = "accountlocked", nullable = false)
//    private boolean accountLocked;
//
//    @Column(name = "credentialsexpired", nullable = false)
//    private boolean credentialsExpired;
//
//    @Column(name = "accountenabled", nullable = false)
//    private boolean accountEnabled;

//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public void setAccountExpired(boolean accountExpired) {
//        this.accountExpired = accountExpired;
//    }
//
//    public void setAccountLocked(boolean accountLocked) {
//        this.accountLocked = accountLocked;
//    }
//
//    public void setCredentialsExpired(boolean credentialsExpired) {
//        this.credentialsExpired = credentialsExpired;
//    }
//
//    public void setAccountEnabled(boolean accountEnabled) {
//        this.accountEnabled = accountEnabled;
//    }

//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isAccountNonExpired() {
//        return !accountExpired;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isAccountNonLocked() {
//        return !accountLocked;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isCredentialsNonExpired() {
//        return !credentialsExpired;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isEnabled() {
//        return !accountEnabled;
//    }

}
