package com.caring.wxrs.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 *
 * @author james
 */
public abstract class AbstractTokenHandler implements TokenHandler {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected byte[] createHmac(byte[] data) {
        try {
            Mac hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(getSecurityKey(), HMAC_ALGO));
            return hmac.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    @Override
    public String crypt(String data) {
        return new BCryptPasswordEncoder().encode(StringUtils.isEmpty(data) ? "" : data);
    }

    @Override
    public boolean matches(String raw, String encoded) {
        return new BCryptPasswordEncoder().matches(String.valueOf(raw), encoded);
    }

    protected String toBase64(byte[] content) {
        return DatatypeConverter.printBase64Binary(content);
    }

    protected byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }

    protected String toHex(byte[] content) {
        return DatatypeConverter.printHexBinary(content);
    }

    protected byte[] fromHex(String content) {
        return DatatypeConverter.parseHexBinary(content);
    }

    protected abstract byte[] getSecurityKey();
}
