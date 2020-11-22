package com.plutus.system.service;

import com.plutus.system.model.request.ChangeBalanceRequest;
import com.plutus.system.model.request.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;

import java.math.BigDecimal;
import java.util.Collection;

public interface TransferService {
    Collection<TransferInfo> getAll(Long accountId);
    void changeBalance(ChangeBalanceRequest request);
    TransferInfo makeTransfer(MakeTransferRequest request);

}
