package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FindAccountRequest {
    private BigInteger accountId;
    private String accountNumber;
    private BigInteger clientId;
}
