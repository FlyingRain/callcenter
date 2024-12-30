package com.flyingrain.freeswitch.model;

import com.flyingrain.freeswitch.constants.FsMsgNameConstants;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;


@Data
@ToString
@Slf4j
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

    private Date channelHangupTime;

    private String sipCallId;

    private String channelName;

    private Map<String, String> variables;

    public static FsEvent fromMap(Map<String, String> headers) {
        FsEvent event = new FsEvent();
        log.info("header size:[{}]", headers.size());
        event.setEventName(headers.get(FsMsgNameConstants.EVENT_NAME));
        event.setChannelDirection(headers.get(FsMsgNameConstants.CHANNEL_DIRECTION));
        event.setCallerName(headers.get(FsMsgNameConstants.CALLER_NAME));
        event.setCallerNumber(headers.get(FsMsgNameConstants.CALLER_NUMBER));
        event.setEventDate(parseDate(headers.get(FsMsgNameConstants.EVENT_TIME)));
        event.setChannelAnswerTime(parseDate(headers.get(FsMsgNameConstants.CHANNEL_ANSWER_TIME)));
        event.setChannelCreateTime(parseDate(headers.get(FsMsgNameConstants.CHANNEL_CREATE_TIME)));
        event.setChannelDestNumber(headers.get(FsMsgNameConstants.CALLED_NUMBER));
        event.setChannelHangupTime(parseDate(headers.get(FsMsgNameConstants.CHANNEL_HANGUP_TIME)));
        event.setNetworkAddr(headers.get(FsMsgNameConstants.NETWORK_ADDR));
        event.setChannelUniqueId(headers.get(FsMsgNameConstants.CHANNEL_ID));
        event.setSipCallId(headers.get(FsMsgNameConstants.SIP_CALL_ID));
        event.setChannelName(headers.get(FsMsgNameConstants.CHANNEL_NAME));
        return event;
    }


    public static FsEvent fromESLEvent(EslEvent event) {
        return fromMap(event.getEventHeaders());
    }

    private static Date parseDate(String date) {
        try {
            long timestamp = Long.parseLong(date) / 1000;
            return timestamp > 1000 ? new Date(timestamp) : null;
        } catch (Exception e) {
            log.error("date:[{}]", date);
            log.error("parse date failed!", e);
        }
        return null;
    }
}
