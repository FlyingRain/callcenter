package com.flyingrain.freeswitch.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FsSession {

    private String channelUniqueId;

    private String callId;

    private String number;

    public static FsSession fromFsEvent(FsEvent fsEvent) {
        FsSession session = new FsSession();
        session.setCallId(fsEvent.getSipCallId());
        session.setChannelUniqueId(fsEvent.getChannelUniqueId());
        // sofia/internal/1001@192.168.0.116:64588
        String channelName = fsEvent.getChannelName();
        if (StringUtils.isNotBlank(channelName)) {
            if (channelName.contains("@")) {
                String[] numberInfo = channelName.split("@");
                channelName = numberInfo[0];
            }
            channelName = channelName.substring(channelName.lastIndexOf("/") + 1);
        }
        session.setNumber(channelName);
        return session;
    }

}
