package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;

public class CommentFilter extends CommonFilter {
    private Long id;
    private Long memberId;
    private Long goodsId;

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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    protected String getSuffix() {
        return "com";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (id != null) {
            handleAND(externalFilter);
            externalFilter.append("com.id = :id");
            withParameter("id", id);
        }
        if (memberId != null) {
            handleAND(externalFilter);
            externalFilter.append("com.member.id = :memberId");
            withParameter("memberId", memberId);
        }
        if (goodsId != null) {
            handleAND(externalFilter);
            externalFilter.append("com.order.goods.id = :goodsId");
            withParameter("goodsId", goodsId);
        }

        return withExtenalFilter(externalFilter.toString());
    }
}
