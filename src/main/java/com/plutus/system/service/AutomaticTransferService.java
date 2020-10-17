package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.AutomaticTransfer;

import java.util.Collection;

public interface AutomaticTransferService {
    Collection<AutomaticTransfer> getAutomaticTransfersForAccount(Account account);

    AutomaticTransfer create(AutomaticTransfer transfer);

    void disable(AutomaticTransfer transfer);

    void delete(AutomaticTransfer transfer);
}
