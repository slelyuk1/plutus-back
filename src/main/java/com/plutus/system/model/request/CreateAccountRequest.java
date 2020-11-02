package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateAccountRequest {
    private String number;
    private String pin;
    private Long creditTariffId;
    private BigInteger ownerId;
}
