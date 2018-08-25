package com.caring.service.sms.sohu.sendcloud.sdk.model;

import com.caring.service.sms.sohu.sendcloud.sdk.exception.ContentException;

public interface Content {

    public boolean useTemplate();

    public boolean validate() throws ContentException;
}
