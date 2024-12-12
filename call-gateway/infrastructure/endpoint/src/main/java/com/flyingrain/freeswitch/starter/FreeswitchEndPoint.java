package com.flyingrain.freeswitch.starter;


import com.flyingrain.freeswitch.eventlistener.ESLEventListener;
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

    @PostConstruct
    public void init() {
        InboundClientOption inboundClientOption = new InboundClientOption();
        inboundClientOption.addListener(eslEventListener);
        inboundClientOption.addEvents("all");
        ServerOption serverOption = new ServerOption("192.168.100.197", 8021);
        inboundClientOption.addServerOption(serverOption);
        NettyInboundClient nettyInboundClient = new NettyInboundClient(inboundClientOption);
        nettyInboundClient.start();

        while(serverOption.state()!= ConnectState.AUTHED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("waiting for inbound client,server state:[{}]", serverOption.state());
        }
        String id = nettyInboundClient.sendAsyncApiCommand("192.168.100.197:8021", "api", "version");
        log.info("api exe id :[{}]", id);
    }


}
