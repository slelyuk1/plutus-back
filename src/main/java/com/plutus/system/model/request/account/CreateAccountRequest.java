package com.plutus.system.model.request.account;

import com.plutus.system.validation.annotation.PinCode;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class CreateAccountRequest {
    @CreditCardNumber
    private String number;
    @PinCode
    private String pin;
    @NotNull
    private BigInteger creditTariffId;
    @NotNull
    private BigInteger ownerId;
}
