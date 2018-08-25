package com.caring.service;

/**
 *
 * @author james
 */
public class CaringServiceException extends RuntimeException {

    public CaringServiceException(String message) {
        super(message);
    }

    public CaringServiceException(String message, Throwable t) {
        super(message, t);
    }

    public CaringServiceException(Throwable t) {
        super(t);
    }
}
