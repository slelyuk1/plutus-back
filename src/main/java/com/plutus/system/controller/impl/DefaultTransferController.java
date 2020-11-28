package com.plutus.system.controller.impl;

import com.plutus.system.controller.TransferController;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.request.transfer.ChangeBalanceRequest;
import com.plutus.system.model.request.transfer.FindTransferRequest;
import com.plutus.system.model.request.transfer.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;
import com.plutus.system.service.TransferService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DefaultTransferController implements TransferController {
    private final TransferService service;

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ATM.getGrantedAuthority())")
    public Collection<TransferInfo> getAccountTransfers() {
        FindTransferRequest request = new FindTransferRequest();
        request.setCreatorId(SecurityHelper.getPrincipalFromSecurityContext());
        return service.find(Optional.of(request)).stream()
                .map(TransferInfo::fromTransfer)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    public Collection<TransferInfo> getAll() {
        return service.find(Optional.empty()).stream()
                .map(TransferInfo::fromTransfer)
                .collect(Collectors.toList());
    }

    @Override
    public TransferInfo changeBalance(ChangeBalanceRequest request) {
        BigInteger toChangeBalanceId = request.getAccountId();
        BigInteger principal = SecurityHelper.getPrincipalFromSecurityContext();
        if (toChangeBalanceId != null && !principal.equals(toChangeBalanceId)) {
            return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> {
                try {
                    return TransferInfo.fromTransfer(service.changeBalance(request));
                } catch (NotExistsException e) {
                    throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
                }
            });
        }
        request.setAccountId(principal);
        return TransferInfo.fromTransfer(service.changeBalance(request));
    }

    @Override
    public TransferInfo makeTransfer(MakeTransferRequest request) {
        BigInteger fromId = request.getFromId();
        BigInteger principal = SecurityHelper.getPrincipalFromSecurityContext();
        if (fromId != null && !fromId.equals(principal)) {
            return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> {
                try {
                    return TransferInfo.fromTransfer(service.makeTransfer(request));
                } catch (NotExistsException e) {
                    throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
                }
            });
        }
        request.setFromId(SecurityHelper.getPrincipalFromSecurityContext());
        return TransferInfo.fromTransfer(service.makeTransfer(request));
    }
}
