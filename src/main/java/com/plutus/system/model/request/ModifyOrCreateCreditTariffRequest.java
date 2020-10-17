package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ModifyOrCreateCreditTariffRequest {
    private Long id;
    private String name;
    private Integer percent;
    private BigDecimal limit;
}
