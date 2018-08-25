package com.caring.wxrs;

/**
 *
 * @author james
 */
public class ExceptionRepresentation {

    private String message;

    public ExceptionRepresentation(String localizedMessage) {
        this.message = localizedMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
