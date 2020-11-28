package com.plutus.system.model.request.account;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FindOneAccountRequest {
    private BigInteger accountId;
    private String accountNumber;
}
