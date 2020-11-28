package com.plutus.system.model.request.client;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FindClientRequest {
    private BigInteger clientId;
    private String email;
}
