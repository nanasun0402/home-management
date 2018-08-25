package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;
import org.apache.commons.lang3.StringUtils;

public class MemberFilter extends CommonFilter {
    private Long id;
    private String username;
    private String mobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected String getSuffix() {
        return "m";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (id != null) {
            handleAND(externalFilter);
            externalFilter.append("m.id = :id");
            withParameter("id", id);
        }
        if (StringUtils.isNotEmpty(mobile)) {
            handleBooleanOperation(externalFilter);
            externalFilter.append("m.mobile LIKE :mobile");
            withParameter("mobile", handleLIKE(mobile));
        }
        if (StringUtils.isNotEmpty(username)) {
            handleBooleanOperation(externalFilter);
            externalFilter.append("m.username LIKE :username");
            withParameter("username", handleLIKE(username));
        }
        return withExtenalFilter(externalFilter.toString());
    }
}
