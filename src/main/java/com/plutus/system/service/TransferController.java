package com.plutus.system.service;

import com.plutus.system.model.request.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;

import java.math.BigDecimal;
import java.util.Collection;

public interface TransferController {
    Collection<TransferInfo> getAll(Long accountId);
    void changeBalance(Long accountId, BigDecimal amount);
    TransferInfo makeTransfer(Long accountId, MakeTransferRequest request);

}
