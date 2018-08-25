package com.caring.service.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author james
 */
@Service
public class JmsEventSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(JmsEvent event) {
        jmsTemplate.convertAndSend("caring.event", event);
    }
}
