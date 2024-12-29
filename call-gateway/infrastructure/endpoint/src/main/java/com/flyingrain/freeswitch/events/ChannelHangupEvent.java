package com.flyingrain.freeswitch.events;

import com.flyingrain.freeswitch.model.FsEvent;
import org.springframework.context.ApplicationEvent;

public class ChannelHangupEvent extends ApplicationEvent {

    public ChannelHangupEvent(FsEvent source) {
        super(source);
    }
}
