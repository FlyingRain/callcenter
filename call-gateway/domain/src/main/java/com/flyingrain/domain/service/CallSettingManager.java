package com.flyingrain.domain.service;

import com.flyingrain.domain.models.CallInSetting;
import com.flyingrain.domain.models.CallSetting;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class CallSettingManager {

    private Map<String, String> callSettingMap = new ConcurrentHashMap<>();

    private Map<String, String> showNumberMap = new ConcurrentHashMap<>();

    public Boolean managerSetting(CallSetting callSetting) {
        showNumberMap.put(callSetting.getCurrentAgent(), callSetting.getShowNumber());
        List<CallInSetting> callInSettings = callSetting.getCallInMap();
        if (!CollectionUtils.isEmpty(callInSettings)) {
            callSettingMap = callInSettings.stream().collect(Collectors.toMap(CallInSetting::getCallInNumber,CallInSetting::getAgent));
        }
        return true;
    }

    public String findAgent(String callerNumber) {
        return callSettingMap.get(callerNumber);
    }
}
