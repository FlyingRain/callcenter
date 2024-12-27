package com.flyingrain.domain.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CallRecord {

    private String callerNumber;

    private String calleeNumber;

    private String callerName;

    private String calleeName;

    private String showNumber;

    private Date callDate;

    private Long callDuration;

}
