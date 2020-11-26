package com.plutus.system.controller.impl;

import com.plutus.system.controller.CreditTariffController;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;
import com.plutus.system.service.CreditTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultCreditTariffController implements CreditTariffController {

    private static final String NOT_FOUND_FOR_DELETION = "Credit tariff with id %d couldn't be found for deletion!";

    private final CreditTariffService service;

    @Override
    public CreditTariffInfo createOrModify(ModifyOrCreateCreditTariffRequest request) {
        try {
            CreditTariff createdOrModified = service.createOrModify(request);
            return CreditTariffInfo.fromCreditTariff(createdOrModified);
        } catch (NotExistsException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(BigInteger creditTariffId) {
        if (!service.delete(creditTariffId)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(NOT_FOUND_FOR_DELETION, creditTariffId));
        }
    }

    @Override
    public void assignToAccount(@Valid AssignCreditTariffToAccountRequest request) {
        try {
            service.assignToAccount(request);
        } catch (NotExistsException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Collection<CreditTariffInfo> getAll() {
        return service.getAll().stream()
                .map(CreditTariffInfo::fromCreditTariff)
                .collect(Collectors.toList());
    }
}
