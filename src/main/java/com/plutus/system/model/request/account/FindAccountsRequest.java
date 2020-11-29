package com.plutus.system.model.request.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class FindAccountsRequest {
    @NotNull
    private BigInteger clientId;
}
