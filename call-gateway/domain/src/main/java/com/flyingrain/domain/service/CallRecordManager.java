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
        String calleeNumber = callRecord.getCalleeNumber();
        if ("1002".equals(calleeNumber)) {
            calleeNumber = "17626189012";
        }
        callTrace.put(calleeNumber, callRecord);

    }
}
