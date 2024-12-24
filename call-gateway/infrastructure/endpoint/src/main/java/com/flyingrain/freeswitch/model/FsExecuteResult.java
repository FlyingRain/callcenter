package com.flyingrain.freeswitch.model;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class FsExecuteResult {

    private String result;

    private String msg;

    private boolean success = false;

    public static FsExecuteResult fromMap(Map<String, String> headers) {
        FsExecuteResult result = new FsExecuteResult();
        result.result = headers.get(FsMsgNameConstants.CONTENT_TYPE);
        result.msg = headers.get(FsMsgNameConstants.REPLY_TEXT);
        result.success = "+OK".equals(result.msg);
        return result;
    }

}
