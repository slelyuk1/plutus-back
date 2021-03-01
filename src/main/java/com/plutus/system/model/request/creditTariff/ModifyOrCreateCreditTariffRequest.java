package com.plutus.system.model.request.creditTariff;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class ModifyOrCreateCreditTariffRequest {
    private BigInteger id;
    private String name;
    @Range(min = 0, max = 100)
    private Integer percent;
    @Min(0)
    private BigDecimal limit;
}
