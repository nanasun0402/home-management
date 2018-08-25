package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;
import org.apache.commons.lang3.StringUtils;

public class GoodsFilter extends CommonFilter {
    private Long id;
    private String name;
    private String type;
    private String label;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    protected String getSuffix() {
        return "g";
    }

    @Override
    public PageFilter buildFilter() {
        StringBuilder externalFilter = new StringBuilder(super.buildFilter().getExternalFilter());
        if (id != null) {
            handleAND(externalFilter);
            externalFilter.append("g.id = :id");
            withParameter("id", id);
        }
        if (StringUtils.isNotEmpty(name)) {
            handleBooleanOperation(externalFilter);
            externalFilter.append("g.name LIKE :name");
            withParameter("name", handleLIKE(name));
        }
        if (StringUtils.isNotEmpty(type)) {
            handleBooleanOperation(externalFilter);
            externalFilter.append("g.type LIKE :type");
            withParameter("type", handleLIKE(type));
        }
        if (StringUtils.isNotEmpty(label)) {
            handleBooleanOperation(externalFilter);
            externalFilter.append("g.label LIKE :label");
            withParameter("label", handleLIKE(label));
        }
        return withExtenalFilter(externalFilter.toString());
    }
}
