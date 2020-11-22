package com.plutus.system.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ChangeBalanceRequest {
    private Long accountId;
    private BigDecimal amount;
}
