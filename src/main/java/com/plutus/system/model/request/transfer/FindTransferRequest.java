package com.plutus.system.model.request.transfer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FindTransferRequest {
    private BigInteger creatorId;
}
