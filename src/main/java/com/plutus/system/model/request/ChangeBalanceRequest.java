package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeBalanceRequest {
    private Long accountId;
    private BigDecimal amount;
}
