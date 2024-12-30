package com.flyingrain.freeswitch.events;

import com.flyingrain.freeswitch.model.FsEvent;
import org.springframework.context.ApplicationEvent;

public class ChannelAnswerEvent extends ApplicationEvent {

    public ChannelAnswerEvent(FsEvent fsEvent) {
        super(fsEvent);
    }
}
