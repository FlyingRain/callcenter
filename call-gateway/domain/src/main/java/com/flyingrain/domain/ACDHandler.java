package com.flyingrain.domain;

import com.flyingrain.domain.models.CallInInfo;
import com.flyingrain.domain.models.UserInfo;

public interface ACDHandler {

    UserInfo dispatch(CallInInfo callInInfo);

}
