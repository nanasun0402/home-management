package com.caring.service.im;

import com.caring.service.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author james
 */
@Service
public class IMCallService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IMConfig config;

    @Autowired
    private IMHttpRequest request;

    public IMResult postRequest(String rsPath, IMExchangeMessage sendMessage) {
        LOG.info(ServiceUtils.toJson(sendMessage));
        return request.post(config.getImServiceHost() + rsPath, sendMessage);
    }
}
