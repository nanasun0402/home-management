package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;
import org.apache.commons.lang3.StringUtils;

public class ShoppingCartFilter extends CommonFilter {
    private Long id;
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    protected String getSuffix() {
        return "sc";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (id != null) {
            handleAND(externalFilter);
            externalFilter.append("sc.id = :id");
            withParameter("id", id);
        }
        if (memberId != null) {
            handleAND(externalFilter);
            externalFilter.append("sc.member.id = :memberId");
            withParameter("memberId", memberId);
        }

        return withExtenalFilter(externalFilter.toString());
    }
}
