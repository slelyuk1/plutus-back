package com.plutus.system.model.request;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class CreateAccountRequest {
    // todo custom validator
    @NotEmpty
    private String number;
    // TODO: 11/3/2020 custom validator
    @NotEmpty
    private String pin;
    // TODO: 11/3/2020 Make @NotNull when credit tariffs are implemented
    @Nullable
    private BigInteger creditTariffId;
    @NotNull
    private BigInteger ownerId;
}
