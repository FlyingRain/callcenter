package com.flyingrain.freeswitch.model;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import lombok.Data;

import java.util.Date;
import java.util.Map;


@Data
public class FsEvent {

    private String eventName;

    private String channelUniqueId;

    private Date eventDate;

    private String channelDirection;

    private String callerName;

    private String callerNumber;

    private String origCallerName;

    private String origCallerNumber;

    private String networkAddr;

    private String channelDestNumber;

    private Date channelCreateTime;

    private Date channelAnswerTime;

    private Map<String,String> variables;

    public static FsEvent fromMap(Map<String,String> headers) {
        FsEvent event = new FsEvent();

        return event;
    }

    public static FsEvent fromESLEvent(EslEvent event) {
        FsEvent fsEvent = new FsEvent();

        return fsEvent;
    }
}
