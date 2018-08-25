package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author james
 */
public class UserFilter extends CommonFilter {

    private Long id;
    private String username;

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

    @Override
    protected String getSuffix() {
        return "u";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (id != null) {
            handleAND(externalFilter);
            externalFilter.append("u.id = :id");
            withParameter("id", id);
        }
        if (StringUtils.isNotEmpty(username)) {
            handleAND(externalFilter);
            externalFilter.append("u.username LIKE :username");
            withParameter("username", handleLIKE(username));
        }
        return withExtenalFilter(externalFilter.toString());
    }
}
