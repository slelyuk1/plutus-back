package com.plutus.system.controller.impl;

import com.plutus.system.controller.TransferController;
import com.plutus.system.model.request.ChangeBalanceRequest;
import com.plutus.system.model.request.FindClientRequest;
import com.plutus.system.model.request.MakeTransferRequest;
import com.plutus.system.model.response.AccountInfo;
import com.plutus.system.model.response.TransferInfo;
import com.plutus.system.service.TransferService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class DefaultTransferController implements TransferController {

    private final TransferService service;


    @Override
    public Collection<TransferInfo> getAll(Optional<Long> maybeAccountId) {
        Long accountId = maybeAccountId.orElseGet(SecurityHelper::getPrincipalFromSecurityContext);
        return service.getAll(accountId);
    }

    @Override
    public void changeBalance(@Valid ChangeBalanceRequest request) {
        service.changeBalance(request);
    }

    @Override
    public TransferInfo makeTransfer(@Valid MakeTransferRequest request) {
        return service.makeTransfer(request);
    }
}
