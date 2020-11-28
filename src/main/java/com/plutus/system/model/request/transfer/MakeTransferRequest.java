package com.plutus.system.model.request.transfer;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class MakeTransferRequest {

    private BigInteger fromId;

    @NotNull
    @CreditCardNumber
    private String toId;

    @DecimalMin(value = "0.0", inclusive = false)
    @NotNull
    private BigDecimal amount;

    private String description;
}
