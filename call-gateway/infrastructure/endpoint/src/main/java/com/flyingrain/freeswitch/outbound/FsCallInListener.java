package com.flyingrain.freeswitch.outbound;

import com.flyingrain.freeswitch.model.FsEvent;
import io.netty.channel.Channel;

public interface FsCallInListener {

    void onCallIn(FsEvent fsEvent, Channel channel);

}