package com.caring.wxrs.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author james
 */
@Component
@PropertySources({
    @PropertySource("classpath:security.properties")
})
public class AuthenticateTokenHandler extends AbstractTokenHandler {

    @Value("token.wechat.secret")
    private String securityKey;

    @Override
    protected byte[] getSecurityKey() {
        return securityKey.getBytes();
    }

    @Override
    public String createToken(Object data) {
        String openId = data.toString();
        byte[] userBytes = openId.getBytes();
        byte[] hash = createHmac(userBytes);
        return toHex(new StringBuilder(170).append(toBase64(userBytes))
                .append(SEPARATOR).append(toBase64(hash)).toString().getBytes());
    }

    @Override
    public Object parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("the token can not be empty !!");
        }
        token = new String(fromHex(token));
        final String[] parts = token.split(SEPARATOR_SPLITTER);
        if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
            try {
                final byte[] userBytes = fromBase64(parts[0]);
                final byte[] hash = fromBase64(parts[1]);
                boolean validHash = Arrays.equals(createHmac(userBytes), hash);
                if (validHash) {
                    String xtoken = new String(userBytes);
                    int pos = xtoken.indexOf(".");
                    return new XWxToken(xtoken.substring(0, pos), xtoken.substring(pos + 1));
                }
            } catch (IllegalArgumentException e) {
                LOG.error("ERROR: {}", e);
            }
        }
        throw new IllegalArgumentException("无效的token");
    }
}
