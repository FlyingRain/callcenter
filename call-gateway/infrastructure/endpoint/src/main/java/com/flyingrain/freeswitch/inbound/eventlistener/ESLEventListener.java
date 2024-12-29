package com.flyingrain.freeswitch.inbound.eventlistener;

import com.flyingrain.freeswitch.FsChannelSessionManager;
import com.flyingrain.freeswitch.constants.FsEventNameEnum;
import com.flyingrain.freeswitch.events.ChannelAnswerEvent;
import com.flyingrain.freeswitch.model.FsEvent;
import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ESLEventListener implements IEslEventListener {

    private Logger logger = LoggerFactory.getLogger(ESLEventListener.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void eventReceived(String addr, EslEvent event) {
        logger.info("receive event:[{}],event:[{}]", addr, event);
        Map<EslHeaders.Name, String> messageHeaders = event.getMessageHeaders();
        Map<String, String> eventHeaders = event.getEventHeaders();
        List<String> results = event.getEventBodyLines();
        logger.info("results:[{}]", results);
        logger.info("---------------------------------------------------------------messageHeaders-----------------------------------------------------------------------------------:[{}]", messageHeaders);
        eventHeaders.forEach((k, v) -> {
            logger.info("eventHeaders: key:[{}],value:[{}]", k, v);
        });
        FsEventNameEnum fsEventNameEnum = FsEventNameEnum.getByCode(event.getEventName());
        if (fsEventNameEnum == null) {
            return;
        }
        switch (fsEventNameEnum) {
            case CHANNEL_CREATE:
            case CHANNEL_HANGUP_COMPLETE:
                logger.info("channel event:[{}]", fsEventNameEnum);
                break;
            case CHANNEL_ANSWER:
                applicationEventPublisher.publishEvent(new ChannelAnswerEvent(FsEvent.fromESLEvent(event)));
                break;
            default:
                logger.warn("unknown event name:[{}]", fsEventNameEnum);
                break;
        }
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
