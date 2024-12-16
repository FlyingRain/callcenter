package com.flyingrain.freeswitch.eventlistener;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ESLEventListener implements IEslEventListener {

    private Logger logger = LoggerFactory.getLogger(ESLEventListener.class);

    @Override
    public void eventReceived(String addr, EslEvent event) {
        logger.info("receive event:[{}],event:[{}]", addr, event);
        Map<EslHeaders.Name, String> messageHeaders = event.getMessageHeaders();
        Map<String, String> eventHeaders = event.getEventHeaders();
        List<String> results = event.getEventBodyLines();
        logger.info("results:[{}]", results);
        logger.info("messageHeaders:[{}]", messageHeaders);
        logger.info("eventHeaders:[{}]", eventHeaders);
    }

    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {
        logger.info("receive background job event:[{}],event:[{}]", addr, event);
        Map<EslHeaders.Name, String> messageHeaders = event.getMessageHeaders();
        Map<String, String> eventHeaders = event.getEventHeaders();
        logger.info("messageHeaders:[{}]", messageHeaders);
        logger.info("eventHeaders:[{}]", eventHeaders);
        logger.info("body:[{}]", event.getEventBodyLines());
    }
}
