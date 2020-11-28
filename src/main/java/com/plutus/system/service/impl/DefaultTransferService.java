package com.plutus.system.service.impl;

import com.plutus.system.exception.InsufficientBalanceException;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Transfer;
import com.plutus.system.model.request.transfer.ChangeBalanceRequest;
import com.plutus.system.model.request.transfer.FindTransferRequest;
import com.plutus.system.model.request.transfer.MakeTransferRequest;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.TransferRepository;
import com.plutus.system.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

// todo credit tariff

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultTransferService implements TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    @Override
    public Collection<Transfer> find(Optional<FindTransferRequest> maybeRequest) {
        if (maybeRequest.isPresent()) {
            FindTransferRequest request = maybeRequest.get();
            return accountRepository.findById(request.getCreatorId())
                    .map(account -> {
                        Transfer toSearch = new Transfer();
                        toSearch.setCreator(account);
                        return toSearch;
                    })
                    .map(toSearch -> transferRepository.findAll(Example.of(toSearch)))
                    .orElse(Collections.emptyList());
        }
        return transferRepository.findAll();
    }

    @Override
    public Transfer changeBalance(ChangeBalanceRequest request) {
        BigDecimal amountDelta = request.getAmount();
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotExistsException("Account", request.getAccountId()));
        BigDecimal resultingMoney = account.getMoney().add(amountDelta);

        Transfer transfer = new Transfer();
        if (amountDelta.compareTo(BigDecimal.ZERO) >= 0) {
            transfer.setReceiver(account);
        } else {
            transfer.setCreator(account);
            if (!canWithdraw(account, amountDelta.abs())) {
                throw new InsufficientBalanceException();
            }
        }
        account.setMoney(resultingMoney);
        transfer.setAmount(amountDelta.abs());
        accountRepository.save(account);
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer makeTransfer(MakeTransferRequest request) {
        Account creator = accountRepository.findById(request.getFromId())
                .orElseThrow(() -> new NotExistsException("Account", request.getFromId()));
        if (canWithdraw(creator, request.getAmount())) {
            Account toSearchReceiver = new Account();
            toSearchReceiver.setNumber(request.getToId());
            Account receiver = accountRepository.findOne(Example.of(toSearchReceiver))
                    .orElseThrow(() -> new NotExistsException("Account", request.getToId()));
            Transfer toCreate = new Transfer();
            toCreate.setCreator(creator);
            toCreate.setReceiver(receiver);
            toCreate.setAmount(request.getAmount());
            toCreate.setDescription(request.getDescription());

            creator.setMoney(creator.getMoney().subtract(request.getAmount()));
            receiver.setMoney(receiver.getMoney().add(request.getAmount()));

            accountRepository.save(creator);
            accountRepository.save(receiver);
            return transferRepository.save(toCreate);
        }
        throw new InsufficientBalanceException();
    }

    private boolean canWithdraw(Account account, BigDecimal toWithdraw) {
        BigDecimal resultingMoney = account.getMoney().subtract(toWithdraw);
        BigDecimal creditTariffMoney = account.getCreditTariff().getLimit();
        return creditTariffMoney.negate().compareTo(resultingMoney) <= 0;
    }
}
