package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Transfer;
import com.plutus.system.model.request.ChangeBalanceRequest;
import com.plutus.system.model.request.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.TransferRepository;
import com.plutus.system.service.TransferService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class DefaultTransferService implements TransferService {

    private final TransferRepository repository;
    private final AccountRepository accountRepository;


    @Override
    public Collection<TransferInfo> getAll(Long accountId) {
        Long principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(accountId)) {
            return repository.findAllByFromId(accountId); }

        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findAllByFromId(accountId));

    }

    @Override
    public void changeBalance(ChangeBalanceRequest request ) {
        Long principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(request.getAccountId())) {
            Optional<Account> maybeAccount = accountRepository.findById(BigInteger.valueOf(request.getAccountId()));

            if(!maybeAccount.isPresent())
               return;
            Account account= maybeAccount.get();
            account.setMoney(account.getMoney().add(request.getAmount()));
            accountRepository.save(account);
        }
    }

    @Override
    public TransferInfo makeTransfer(MakeTransferRequest request) {
        Optional<Account> maybeAccount = accountRepository.findById(BigInteger.valueOf(request.getFromId()));
        Optional<Account> maybeAccount2 = accountRepository.findById(BigInteger.valueOf(request.getToId()));
        String transferStatus;
        if(maybeAccount.isPresent() && maybeAccount2.isPresent()) {
            changeBalance(new ChangeBalanceRequest(request.getFromId(), request.getAmount().negate()));
            changeBalance(new ChangeBalanceRequest(request.getToId(), request.getAmount()));
            transferStatus = "transfer successful";
        }
        else if(maybeAccount.isPresent()) {
            changeBalance(new ChangeBalanceRequest(request.getFromId(), request.getAmount().negate()));
            transferStatus = "withdrawal successful";
        }
        else if(maybeAccount2.isPresent()){
            changeBalance(new ChangeBalanceRequest(request.getToId(), request.getAmount()));
            transferStatus = "charge account successful";
        }
        else {transferStatus = "account was not found";}

        Transfer transfer= new Transfer();
        transfer.setFromId(request.getFromId());
        transfer.setToId(request.getToId());
        transfer.setAmount(request.getAmount());
        transfer.setCreatedWhen(LocalDateTime.now());
        transfer.setDescription(request.getDescription());
        transfer.setTransferStatus(transferStatus);

        TransferInfo ti = TransferInfo.fromTransfer(transfer);
       repository.save(transfer);
        return ti;
    }
}
