package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.CreditTariff;

public interface CreditTariffCService {
    CreditTariff createOrModify(CreditTariff request);

    void delete(CreditTariff creditTariff);

    void assignToAccount(Account account, CreditTariff request);
}