package com.flyingrain.freeswitch.adapter;

import com.flyingrain.domain.ACDHandler;
import com.flyingrain.domain.models.UserInfo;
import com.flyingrain.freeswitch.FsCommandExecuteHelper;
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
    public void onCallIn(FsEvent fsEvent, FsCommandExecuteHelper executeHelper) {
        log.info("call in ,msg【{}】", fsEvent);
        UserInfo destInfo = acdHandler.dispatch(fsModelTransfer.transfer(fsEvent));
        executeHelper.execute("answer",null);
        executeHelper.execute("playback","tone_stream://%(2000,4000,440,480)");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executeHelper.execute("break",null);

        executeHelper.execute("echo",null);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executeHelper.execute("break",null);
        executeHelper.execute("bridge","user/1003@172.17.0.3");
    }
}
