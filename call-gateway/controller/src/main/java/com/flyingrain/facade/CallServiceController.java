package com.flyingrain.facade;

import com.flyingrain.domain.models.CallRecord;
import com.flyingrain.domain.models.CallSetting;
import com.flyingrain.domain.models.CommonResult;
import com.flyingrain.domain.service.CallOutManager;

import com.flyingrain.domain.service.CallSettingManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CallServiceController {

    @Autowired
    private CallOutManager callService;

    @Autowired
    private CallSettingManager callSettingManager;

    @PostMapping("/makeCall")
    public CommonResult<Boolean> makeCall(@RequestBody CallRecord callRecord) {
        log.info("Make call record: [{}]", callRecord);
        if ("17626189012".equals(callRecord.getCalleeNumber())) {
            callRecord.setCalleeNumber("1002");
        }
        return callService.makeCall(callRecord);
    }


    @PostMapping("/callSettings")
    public CommonResult<Boolean> callSettings(@RequestBody CallSetting callSetting) {
        return CommonResult.success(callSettingManager.managerSetting(callSetting));
    }


}
