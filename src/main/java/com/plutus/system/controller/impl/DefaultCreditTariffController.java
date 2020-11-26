package com.plutus.system.controller.impl;

import com.plutus.system.controller.CreditTariffController;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.FindAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.service.CreditTariffService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultCreditTariffController implements CreditTariffController {

    private static final String CREDIT_TARIFF_NOT_FOUND = "Credit tariff with id %d couldn't be found!";

    private final CreditTariffService creditTariffService;
    private final AccountService accountService;

    @Override
    public CreditTariffInfo createOrModify(ModifyOrCreateCreditTariffRequest request) {
        try {
            CreditTariff createdOrModified = creditTariffService.createOrModify(request);
            return CreditTariffInfo.fromCreditTariff(createdOrModified);
        } catch (NotExistsException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(BigInteger creditTariffId) {
        if (!creditTariffService.delete(creditTariffId)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(CREDIT_TARIFF_NOT_FOUND, creditTariffId));
        }
    }

    @Override
    public void assignToAccount(@Valid AssignCreditTariffToAccountRequest request) {
        try {
            creditTariffService.assignToAccount(request);
        } catch (NotExistsException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Collection<CreditTariffInfo> getAll() {
        return creditTariffService.getAll().stream()
                .map(CreditTariffInfo::fromCreditTariff)
                .collect(Collectors.toList());
    }

    @Override
    public CreditTariffInfo get(Optional<BigInteger> maybeCreditTariffId) {
        BigInteger toFindId = maybeCreditTariffId.orElseGet(() -> {
            FindAccountRequest request = new FindAccountRequest();
            request.setAccountId(SecurityHelper.getPrincipalFromSecurityContext());
            return accountService.find(request)
                    .orElseThrow(() -> new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Account couldn't be found by id from jwt!"))
                    .getCreditTariff()
                    .getId();
        });
        return creditTariffService.getById(toFindId)
                .map(CreditTariffInfo::fromCreditTariff)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(CREDIT_TARIFF_NOT_FOUND, toFindId)));
    }
}
