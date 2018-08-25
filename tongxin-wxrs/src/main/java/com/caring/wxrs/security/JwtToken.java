package com.caring.wxrs.security;

public class JwtToken {
    private final String type;
    private final String data;

    public JwtToken(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return data.split(":")[0];
    }

    public String getName() {
        return data.split(":")[1];
    }

    @Override
    public String toString() {
        return type + "." + data;
    }
}
