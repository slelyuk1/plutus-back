package com.plutus.system.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ATMTokenRequest {
    @NotEmpty
    private String atmKey;
    @NotEmpty
    private String accountId;
    @NotEmpty
    private String pin;
}
