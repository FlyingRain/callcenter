package com.flyingrain.domain.service;

import com.flyingrain.domain.CallService;
import com.flyingrain.domain.models.CallRecord;
import com.flyingrain.domain.models.CommonResult;
import com.flyingrain.domain.models.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallOutManager {

    @Autowired
    private CallService callService;
    @Autowired
    private CallRecordManager callRecordManager;


    public CommonResult<Boolean> makeCall(CallRecord callRecord) {
        callService.makeCall(callRecord);
        callRecordManager.putRecord(callRecord);
        return CommonResult.success(true);
    }

    public CommonResult<Boolean> transfer(TransferRequest transferRequest){
        callService.transferCall(transferRequest);
        return CommonResult.success(true);
    }



}
