package com.flyingrain.freeswitch.adapter;

import com.flyingrain.domain.ACDHandler;
import com.flyingrain.domain.models.UserInfo;
import com.flyingrain.freeswitch.model.FsEvent;
import com.flyingrain.freeswitch.outbound.FsCallInListener;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FreeswitchCallAdapter implements FsCallInListener {

    @Autowired
    private ACDHandler acdHandler;

    @Autowired
    private FsModelTransfer fsModelTransfer;

    @Override
    public void onCallIn(FsEvent fsEvent, Channel channel) {
        log.info("call in ,msg【{}】", fsEvent);
        UserInfo destInfo = acdHandler.dispatch(fsModelTransfer.transfer(fsEvent));
    }
}
