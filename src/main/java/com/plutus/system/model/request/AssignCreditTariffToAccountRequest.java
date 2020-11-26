package com.plutus.system.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class AssignCreditTariffToAccountRequest {
    @NotNull
    private BigInteger accountId;
    @NotNull
    private BigInteger creditTariffId;
}
