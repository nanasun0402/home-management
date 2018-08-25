package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;

public class AddressFilter extends CommonFilter  {

    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    protected String getSuffix() {
        return "a";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (memberId != null) {
            handleAND(externalFilter);
            externalFilter.append("a.member.id = :memberId");
            withParameter("memberId", memberId);
        }

        return withExtenalFilter(externalFilter.toString());
    }
}
