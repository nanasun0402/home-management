package com.caring.wxrs.security;

/**
 *
 * @author james
 */
public interface TokenHandler {

    String HMAC_ALGO = "HmacSHA256";
    String SEPARATOR = ".";
    String SEPARATOR_SPLITTER = "\\.";

    String SET_COOKIE = "Set-Cookie";
    String AUTH_WX_COOKIE_NAME = "X-WX-AUTH-TOKEN";
    String AUTH_MG_COOKIE_NAME = "X-MG-AUTH-TOKEN";
    String AUTH_WX_HEADER_NAME = "X-WX-AUTH-TOKEN";
    String AUTH_MG_HEADER_NAME = "X-MG-AUTH-TOKEN";

    String DMOMAIN = "domain";

    String createToken(Object data);

    Object parseToken(String token);

    String crypt(String data);

    boolean matches(String raw, String encoded);
}
