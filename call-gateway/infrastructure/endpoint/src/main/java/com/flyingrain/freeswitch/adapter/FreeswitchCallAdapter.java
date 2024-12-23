package com.flyingrain.freeswitch.adapter;

import com.flyingrain.domain.CallInHandler;
import com.flyingrain.freeswitch.model.FsEvent;
import com.flyingrain.freeswitch.outbound.CallInListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FreeswitchCallAdapter implements CallInListener {

    @Autowired
    private List<CallInHandler> callInHandlers;

    @Override
    public void onCallIn(FsEvent fsEvent) {

    }
}
