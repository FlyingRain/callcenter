package com.flyingrain.freeswitch.inbound.starter;


import com.flyingrain.domain.models.TransferRequest;
import com.flyingrain.freeswitch.configs.FreeswitchConfig;
import com.flyingrain.freeswitch.inbound.eventlistener.ESLEventListener;
import jakarta.annotation.PostConstruct;
import link.thingscloud.freeswitch.esl.inbound.NettyInboundClient;
import link.thingscloud.freeswitch.esl.inbound.option.ConnectState;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FreeswitchEndPoint {

    @Autowired
    private ESLEventListener eslEventListener;

    @Autowired
    private FreeswitchConfig freeswitchConfig;

    private String addr;


    private NettyInboundClient nettyInboundClient;


    @PostConstruct
    public void init() {
        addr = freeswitchConfig.getFsInboundAddr() + ":" + freeswitchConfig.getFsInboundPort();
        InboundClientOption inboundClientOption = new InboundClientOption();
        inboundClientOption.addListener(eslEventListener);
        inboundClientOption.addEvents("CHANNEL_HANGUP_COMPLETE", "CHANNEL_ANSWER", "BACKGROUND_JOB", "CHANNEL_CREATE");
        ServerOption serverOption = new ServerOption(freeswitchConfig.getFsInboundAddr(), freeswitchConfig.getFsInboundPort());
        serverOption.password("FlyingRain");
        inboundClientOption.addServerOption(serverOption);
        NettyInboundClient nettyInboundClient = new NettyInboundClient(inboundClientOption);
        nettyInboundClient.start();

        while (serverOption.state().ordinal() < ConnectState.AUTHED.ordinal()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("waiting for inbound client,server state:[{}]", serverOption.state());
        }
        this.nettyInboundClient = nettyInboundClient;
        String id = nettyInboundClient.sendAsyncApiCommand(addr, "api", "version");
        log.info("api exe id :[{}]", id);
    }

    public void doubleCall(String callerNumber, String calleeNumber, String showNumber) {
        String args = "{sip_h_X-MY-HEADER=2222}user/" + callerNumber + " &bridge({origination_caller_id_name=" + showNumber + ",origination_caller_id_number=" + showNumber + "}user/" + calleeNumber + ")";
        log.info("call args:[{}]", args);
        String callOutCommandId = nettyInboundClient.sendAsyncApiCommand(addr, "originate", args);
        log.info("callOutCommandId :[{}]", callOutCommandId);
    }

    public void transferCall(String channelId, String targetNumber) {
        nettyInboundClient.sendAsyncApiCommand(addr, "uuid_transfer", channelId + " -bleg 'set:hangup_after_bridge=false,bridge:user/" + targetNumber + "' inline");
    }


}
