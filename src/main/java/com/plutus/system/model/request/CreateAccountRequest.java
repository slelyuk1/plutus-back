package com.plutus.system.model.request;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String pin;
    private Long creditTariffId;
    private Long userId;
}
