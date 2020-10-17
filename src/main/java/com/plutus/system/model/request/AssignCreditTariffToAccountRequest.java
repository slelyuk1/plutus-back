package com.plutus.system.model.request;

import lombok.Data;

@Data
public class AssignCreditTariffToAccountRequest {
    private Long accountId;
    private Long creditTariffId;
}
