package com.plutus.system.service;

import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;

import java.math.BigInteger;
import java.util.Collection;

public interface CreditTariffService {
    CreditTariff createOrModify(ModifyOrCreateCreditTariffRequest request) throws NotExistsException;

    boolean delete(BigInteger creditTariffId);

    void assignToAccount(AssignCreditTariffToAccountRequest request) throws NotExistsException;

    Collection<CreditTariff> getAll();
}