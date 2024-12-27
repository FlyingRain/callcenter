package com.flyingrain.domain.service;

import com.flyingrain.domain.ACDHandler;
import com.flyingrain.domain.models.CallInInfo;
import com.flyingrain.domain.models.CallRecord;
import com.flyingrain.domain.models.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CallInDispatch implements ACDHandler {

    @Autowired
    private CallRecordManager callRecordManager;

    @Autowired
    private CallSettingManager callSettingManager;

    @Override
    public UserInfo dispatch(CallInInfo callInInfo) {
        String agentNumber = callSettingManager.findAgent(callInInfo.getPhone());
        if (StringUtils.isEmpty(agentNumber)) {
            CallRecord callRecord = callRecordManager.getCallRecord(callInInfo.getPhone());
            agentNumber = callRecord==null ? "1003" : callRecord.getCallerNumber();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setRegisterNumber(agentNumber);
        return userInfo;
    }
}
