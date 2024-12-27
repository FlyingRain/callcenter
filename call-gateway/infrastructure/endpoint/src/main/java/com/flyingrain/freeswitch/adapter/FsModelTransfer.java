package com.flyingrain.freeswitch.adapter;

import com.flyingrain.domain.models.CallInInfo;
import com.flyingrain.freeswitch.model.FsEvent;
import org.springframework.stereotype.Component;

@Component
public class FsModelTransfer {


    public CallInInfo transfer(FsEvent fsEvent) {
        CallInInfo callInInfo = new CallInInfo();
        callInInfo.setPhone(fsEvent.getCallerNumber());
        if ("1002".equals(fsEvent.getCallerNumber())) {
            callInInfo.setPhone("17626189012");
        }
        callInInfo.setDestNumber(fsEvent.getChannelDestNumber());
        return callInInfo;
    }

}
