package com.flyingrain.domain.models;

import lombok.Data;

import java.util.List;

@Data
public class CallSetting {

    private String currentAgent;

    private String showNumber;

    private List<CallInSetting> callInMap;



}
