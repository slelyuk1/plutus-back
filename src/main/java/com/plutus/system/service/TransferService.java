package com.plutus.system.service;

import com.plutus.system.model.entity.Transfer;
import com.plutus.system.model.request.transfer.ChangeBalanceRequest;
import com.plutus.system.model.request.transfer.FindTransferRequest;
import com.plutus.system.model.request.transfer.MakeTransferRequest;

import java.util.Collection;
import java.util.Optional;

public interface TransferService {
    Collection<Transfer> find(Optional<FindTransferRequest> maybeRequest);

    Transfer changeBalance(ChangeBalanceRequest request);

    Transfer makeTransfer(MakeTransferRequest request);
}
