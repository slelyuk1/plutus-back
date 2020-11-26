package com.plutus.system.service.impl;

import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.CreditTariffRepository;
import com.plutus.system.service.CreditTariffService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DefaultCreditTariffService implements CreditTariffService {

    private final CreditTariffRepository creditTariffRepository;
    private final AccountRepository accountRepository;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public CreditTariff createOrModify(ModifyOrCreateCreditTariffRequest request) throws NotExistsException {
        if (request.getId() != null) {
            CreditTariff existing = creditTariffRepository.findById(request.getId())
                    .orElseThrow(() -> new NotExistsException("Credit tariff", request.getId()));
            if (request.getLimit() != null) {
                existing.setLimit(request.getLimit());
            }
            if (request.getName() != null) {
                existing.setName(request.getName());
            }
            if (request.getPercent() != null) {
                existing.setPercent(request.getPercent());
            }
            return creditTariffRepository.save(existing);
        }
        CreditTariff toCreate = new CreditTariff();
        toCreate.setName(request.getName());
        toCreate.setPercent(request.getPercent());
        toCreate.setLimit(request.getLimit());
        // todo may be persistence errors
        return creditTariffRepository.save(toCreate);
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public boolean delete(BigInteger creditTariffId) {
        if (creditTariffRepository.existsById(creditTariffId)) {
            creditTariffRepository.deleteById(creditTariffId);
            return true;
        }
        return false;
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public void assignToAccount(AssignCreditTariffToAccountRequest request) throws NotExistsException {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotExistsException("Account", request.getAccountId()));
        CreditTariff creditTariff = creditTariffRepository.findById(request.getCreditTariffId())
                .orElseThrow(() -> new NotExistsException("Credit tariff", request.getCreditTariffId()));
        account.setCreditTariff(creditTariff);
        accountRepository.save(account);
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<CreditTariff> getAll() {
        return creditTariffRepository.findAll();
    }

    @Override
    public Optional<CreditTariff> getById(BigInteger id) {
        // todo make security refactoring
        return creditTariffRepository.findById(id);
    }
}
