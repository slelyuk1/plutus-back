package com.plutus.system.controller.impl;

import com.plutus.system.controller.CreditTariffController;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.account.FindOneAccountRequest;
import com.plutus.system.model.request.creditTariff.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.creditTariff.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.service.CreditTariffService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultCreditTariffController implements CreditTariffController {

    private static final String CREDIT_TARIFF_NOT_FOUND = "Credit tariff with id %d couldn't be found!";

    private final CreditTariffService creditTariffService;
    private final AccountService accountService;

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    public CreditTariffInfo createOrModify(ModifyOrCreateCreditTariffRequest request) {
        CreditTariff createdOrModified = creditTariffService.createOrModify(request);
        return CreditTariffInfo.fromCreditTariff(createdOrModified);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    public void delete(BigInteger creditTariffId) {
        if (!creditTariffService.delete(creditTariffId)) {
            throw new NotExistsException("Account", creditTariffId);
        }
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    public void assignToAccount(@Valid AssignCreditTariffToAccountRequest request) {
        creditTariffService.assignToAccount(request);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    public Collection<CreditTariffInfo> getAll() {
        return creditTariffService.getAll().stream()
                .map(CreditTariffInfo::fromCreditTariff)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ATM.getGrantedAuthority())")
    public CreditTariffInfo getForCurrentAccount() {
        FindOneAccountRequest request = new FindOneAccountRequest();
        request.setAccountId(SecurityHelper.getPrincipalFromSecurityContext());
        CreditTariff accountCreditTariff = accountService.findAccount(request)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Account couldn't be found by id from jwt!"))
                .getCreditTariff();
        return getInfo(accountCreditTariff.getId());
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public CreditTariffInfo getInfo(BigInteger creditTariffId) {
        return creditTariffService.getById(creditTariffId)
                .map(CreditTariffInfo::fromCreditTariff)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(CREDIT_TARIFF_NOT_FOUND, creditTariffId)));
    }
}
