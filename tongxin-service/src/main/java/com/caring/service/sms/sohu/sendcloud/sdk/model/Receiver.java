package com.caring.service.sms.sohu.sendcloud.sdk.model;

import com.caring.service.sms.sohu.sendcloud.sdk.exception.ReceiverException;

public interface Receiver {

    public boolean useAddressList();

    public boolean validate() throws ReceiverException;

    @Override
    public String toString();
}
