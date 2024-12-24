package com.flyingrain.domain.service;

import com.flyingrain.domain.ACDHandler;
import com.flyingrain.domain.models.CallInInfo;
import com.flyingrain.domain.models.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class CallInDispatch implements ACDHandler {


    @Override
    public UserInfo dispatch(CallInInfo callInInfo) {
        UserInfo userInfo = new UserInfo();
        return userInfo;
    }
}
