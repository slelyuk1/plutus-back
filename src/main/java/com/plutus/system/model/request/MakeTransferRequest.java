package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MakeTransferRequest {
    private Long toId;
    private Long fromId;
    private BigDecimal amount;
    private String description;
}
