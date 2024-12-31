package com.flyingrain.freeswitch;

import com.flyingrain.freeswitch.events.ChannelAnswerEvent;
import com.flyingrain.freeswitch.events.ChannelHangupEvent;
import com.flyingrain.freeswitch.model.FsEvent;
import com.flyingrain.freeswitch.model.FsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FsChannelSessionManager {

    private Map<String, FsSession> callingSessions = new ConcurrentHashMap<>(32);

    private final  Logger logger = LoggerFactory.getLogger(FsChannelSessionManager.class);

    @EventListener(ChannelAnswerEvent.class)
    public void onChannelAnswer(ChannelAnswerEvent channelAnswerEvent) {
        FsEvent fsEvent = (FsEvent) channelAnswerEvent.getSource();
        FsSession fsSession = FsSession.fromFsEvent(fsEvent);
        FsSession oldSession = callingSessions.putIfAbsent(fsSession.getCallId(), fsSession);
        logger.warn("session had exited! old session: [{}], new: [{}]", oldSession, fsSession);
    }

    @EventListener(ChannelHangupEvent.class)
    public void onChannelHangup(ChannelAnswerEvent channelAnswerEvent) {
        FsEvent fsEvent = (FsEvent) channelAnswerEvent.getSource();
        FsSession fsSession = FsSession.fromFsEvent(fsEvent);
        callingSessions.remove(fsSession.getCallId());
    }

    public FsSession getCallingSessionByCallId(String callId) {
        return callingSessions.get(callId);
    }


}
