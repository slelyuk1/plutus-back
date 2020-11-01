package com.plutus.system.listener;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.AccountService;
import com.plutus.system.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Profile("dev")
@Component
@Slf4j
@RequiredArgsConstructor
public class CreateTestClientOnApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    public static final String CLIENT_TEST_NAME_OR_SURNAME = "Test";
    public static final String CLIENT_TEST_EMAIL = "test@gmail.com";
    public static final String TEST_NUMBER = "1234123412341234";
    public static final String TEST_PIN = "1234";

    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        final Client clientToCreate = createTestClientForPersistence();
        Client createdClient = clientRepository.findOne(Example.of(clientToCreate))
                .orElseGet(() -> clientService.create(clientToCreate));
        log.info("Created test client: {}", createdClient);
        final Account accountToCreate = createTestAccountForPersistence(clientToCreate);
        Account createdAccount = accountRepository.findOne(Example.of(accountToCreate))
                .orElseGet(() -> accountService.create(accountToCreate));
        log.info("Created test account: {}", createdAccount);
    }

    private static Client createTestClientForPersistence() {
        Client clientForTest = new Client();
        clientForTest.setName(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setSurname(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setEmail(CLIENT_TEST_EMAIL);
        return clientForTest;
    }

    private static Account createTestAccountForPersistence(Client owner) {
        Account account = new Account();
        account.setNumber(TEST_NUMBER);
        account.setPin(TEST_PIN);
        account.setOwner(owner);
        return account;
    }
}
