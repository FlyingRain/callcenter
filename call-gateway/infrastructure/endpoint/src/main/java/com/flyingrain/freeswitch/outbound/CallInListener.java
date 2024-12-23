package com.flyingrain.freeswitch.outbound;

import com.flyingrain.freeswitch.model.FsEvent;

public interface CallInListener {

    void onCallIn(FsEvent fsEvent);

}
