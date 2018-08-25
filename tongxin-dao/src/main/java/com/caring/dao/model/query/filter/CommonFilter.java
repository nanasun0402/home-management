package com.caring.dao.model.query.filter;

import com.caring.dao.model.query.PageFilter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author james
 */
public abstract class CommonFilter extends PageFilter {

    private String dataFlag;
    private Boolean or;

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public Boolean getOr() {
        return or != null && or;
    }

    public void setOr(Boolean or) {
        this.or = or;
    }

    protected abstract String getSuffix();

    protected void handleBooleanOperation(StringBuilder strBuilder) {
        if (getOr()) {
            handleOR(strBuilder);
        } else {
            handleAND(strBuilder);
        }
    }

    private String getNextExternalFilter() {
        return getNextFilter() == null ? null : "(" + getNextFilter().buildFilter().getExternalFilter() + ")";
    }

    @Override
    public PageFilter buildFilter() {
        String nextExternalFilter = getNextExternalFilter();
        StringBuilder externalFilter = nextExternalFilter != null ? new StringBuilder(nextExternalFilter) : new StringBuilder();
        if (StringUtils.isNotEmpty(dataFlag)) {
            handleAND(externalFilter);
            externalFilter.append(getSuffix()).append(".dataFlag = :dataFlag");
            withParameter("dataFlag", dataFlag);
        }
        return this.withExtenalFilter(externalFilter.toString());
    }
}
