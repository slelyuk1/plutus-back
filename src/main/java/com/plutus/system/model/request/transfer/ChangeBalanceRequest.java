package com.plutus.system.model.request.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ChangeBalanceRequest {
    private BigInteger accountId;
    @NotNull
    private BigDecimal amount;
}
