package com.plutus.system.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ATMTokenRequest {
    @NotEmpty
    private String accountNumber;
    @NotEmpty
    private String pin;
}
