package com.flyingrain.freeswitch.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FreeswitchConfig {

    @Value("${fs.inbound.addr}")
    private String fsInboundAddr;

    @Value("${fs.inbound.port:8021}")
    private Integer fsInboundPort;

    @Value("${fs.inbound.pwd:ClueCon}")
    private String fsInboundPwd;

}
