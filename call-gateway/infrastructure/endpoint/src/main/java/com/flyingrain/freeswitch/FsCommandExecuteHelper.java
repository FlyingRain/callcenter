package com.flyingrain.freeswitch;

import com.flyingrain.freeswitch.model.FsExecuteResult;

public interface FsCommandExecuteHelper {


    FsExecuteResult execute(String command,String args);


    void executeAsync(String command,String args);

}
