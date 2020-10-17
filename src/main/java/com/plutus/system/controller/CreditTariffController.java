package com.plutus.system.controller;

import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;

public interface CreditTariffController {
    CreditTariffInfo createOrModify(ModifyOrCreateCreditTariffRequest request);
    void delete(Long creditTariffId);
    void assignToAccount(AssignCreditTariffToAccountRequest request);
}
