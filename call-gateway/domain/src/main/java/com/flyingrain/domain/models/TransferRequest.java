package com.flyingrain.domain.models;
import lombok.Data;

@Data
public class TransferRequest {

    private String callId;

    private String targetNumber;

}
