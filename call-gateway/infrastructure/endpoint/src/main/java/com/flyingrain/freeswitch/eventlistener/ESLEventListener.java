package com.flyingrain.freeswitch.eventlistener;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import org.springframework.stereotype.Component;

@Component
public class ESLEventListener implements IEslEventListener {
    @Override
    public void eventReceived(String addr, EslEvent event) {

    }

    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {

    }
}
