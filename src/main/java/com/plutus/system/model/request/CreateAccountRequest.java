package com.plutus.system.model.request;

import com.plutus.system.validation.annotation.PinCode;
import com.sun.istack.Nullable;
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
    
    // TODO: 11/3/2020 Make @NotNull when credit tariffs are implemented
    @Nullable
    private BigInteger creditTariffId;
    @NotNull
    private BigInteger ownerId;
}
