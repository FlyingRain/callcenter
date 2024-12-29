package com.flyingrain.domain;

import com.flyingrain.domain.models.CallRecord;
import com.flyingrain.domain.models.TransferRequest;

public interface CallService {

    void makeCall(CallRecord callRecord);

    void transferCall(TransferRequest transferRequest);
}
