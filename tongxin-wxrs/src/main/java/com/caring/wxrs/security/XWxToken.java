package com.caring.wxrs.security;

/**
 *
 * @author james
 */
public class XWxToken {

    private final String type;
    private final String id;

    public XWxToken(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return type + "." + id;
    }

}
