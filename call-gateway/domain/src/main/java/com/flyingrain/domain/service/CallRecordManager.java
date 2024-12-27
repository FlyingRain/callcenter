package com.flyingrain.domain.service;

import com.flyingrain.domain.models.CallRecord;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CallRecordManager {

    private Map<String, CallRecord> callTrace = new HashMap<>();


    public CallRecord getCallRecord(String calleeNumber) {
        return callTrace.get(calleeNumber);
    }

    public void putRecord(CallRecord callRecord) {

        callTrace.put(callRecord.getCalleeNumber(), callRecord);
    }
}
