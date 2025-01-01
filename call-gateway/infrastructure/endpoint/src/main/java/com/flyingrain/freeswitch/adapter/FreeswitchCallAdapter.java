package com.flyingrain.freeswitch.adapter;

import com.flyingrain.domain.ACDHandler;
import com.flyingrain.domain.CallService;
import com.flyingrain.domain.models.CallInInfo;
import com.flyingrain.domain.models.CallRecord;
import com.flyingrain.domain.models.TransferRequest;
import com.flyingrain.domain.models.UserInfo;
import com.flyingrain.freeswitch.FsChannelSessionManager;
import com.flyingrain.freeswitch.FsCommandExecuteHelper;
import com.flyingrain.freeswitch.inbound.starter.FreeswitchEndPoint;
import com.flyingrain.freeswitch.model.FsEvent;
import com.flyingrain.freeswitch.model.FsSession;
import com.flyingrain.freeswitch.outbound.FsCallInListener;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FreeswitchCallAdapter implements FsCallInListener, CallService {

    @Autowired
    private ACDHandler acdHandler;

    @Autowired
    private FsModelTransfer fsModelTransfer;

    @Autowired
    private FreeswitchEndPoint freeswitchEndPoint;

    @Autowired
    private FsChannelSessionManager fsChannelSessionManager;

    @Override
    public void onCallIn(FsEvent fsEvent, FsCommandExecuteHelper executeHelper) {
        log.info("call in ,msg【{}】", fsEvent);
        CallInInfo callInInfo = fsModelTransfer.transfer(fsEvent);
        UserInfo destInfo = acdHandler.dispatch(callInInfo);
        executeHelper.execute("answer", null);
        executeHelper.execute("playback", "tone_stream://%(2000,4000,440,480)");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executeHelper.execute("break", null);

        executeHelper.execute("bridge", "{origination_caller_id_name=" + callInInfo.getPhone() + ",origination_caller_id_number=" + callInInfo.getPhone() + "}user/" + destInfo.getRegisterNumber());
    }

    public void makeCall(CallRecord callRecord) {
        freeswitchEndPoint.doubleCall(callRecord.getCallerNumber(), callRecord.getCalleeNumber(), callRecord.getShowNumber());
    }

    @Override
    public void transferCall(TransferRequest transferRequest) {
        FsSession fsSession = fsChannelSessionManager.getCallingSessionByCallId(transferRequest.getCallId());
        freeswitchEndPoint.transferCall(fsSession.getChannelUniqueId(), transferRequest.getTargetNumber());
    }


}
