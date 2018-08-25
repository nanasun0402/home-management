package com.caring.wxrs;

/**
 *
 * @author james
 */
public class TongxinWxServiceException extends RuntimeException {

    public TongxinWxServiceException(String message) {
        super(message);
    }

    public TongxinWxServiceException(String message, Throwable t) {
        super(message, t);
    }

    public TongxinWxServiceException(Throwable t) {
        super(t);
    }
}
