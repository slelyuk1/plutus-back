package com.plutus.system.controller;

import com.plutus.system.model.request.CreateAutomaticTransferRequest;
import com.plutus.system.model.response.AutomaticTransferInfo;

import java.util.Collection;

public interface AutomaticTransferController {
    Collection<AutomaticTransferInfo> getAll(Long accountId);

    AutomaticTransferInfo create(CreateAutomaticTransferRequest request);

    void disable(Long transferId);

    void delete(Long transferId);
}
