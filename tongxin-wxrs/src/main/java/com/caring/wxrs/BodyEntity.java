package com.caring.wxrs;

import java.util.HashMap;

/**
 *
 * @author james
 */
public class BodyEntity extends HashMap<String, Object> {

    public static BodyEntity create(String fieldName, Object fieldValue) {
        BodyEntity body = new BodyEntity();
        body.put(fieldName, fieldValue);
        return body;
    }

    public BodyEntity withData(String fieldName, Object fieldValue) {
        this.put(fieldName, fieldValue);
        return this;
    }

    public BodyEntity withCode(int code) {
        this.put("code", code);
        return this;
    }

    public BodyEntity withMessage(String message) {
        this.put("message", message);
        return this;
    }
}
