package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class ModifyOrCreateCreditTariffRequest {
    private BigInteger id;
    private String name;
    private Integer percent;
    private BigDecimal limit;
}
