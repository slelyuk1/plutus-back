package com.plutus.system.listener;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindOneAccountRequest;
import com.plutus.system.model.request.client.CreateClientRequest;
import com.plutus.system.model.request.client.FindClientRequest;
import com.plutus.system.model.request.creditTariff.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.service.AccountService;
import com.plutus.system.service.ClientService;
import com.plutus.system.service.CreditTariffService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Profile("dev")
@Component
public class CreateTestClientOnApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    public static final String CREDIT_TARIFF_TEST_NAME = "Test Credit Tariff";
    public static final BigDecimal CREDIT_TARIFF_TEST_LIMIT = BigDecimal.valueOf(500);
    public static final int CREDIT_TARIFF_TEST_PERCENT = 12;

    public static final String CLIENT_TEST_NAME_OR_SURNAME = "Test";
    public static final String CLIENT_TEST_EMAIL = "test@gmail.com";
    public static final String TEST_NUMBER = "4532280486346619";
    public static final String TEST_PIN = "1234";

    private final CreditTariffService creditTariffService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final SecurityHelper securityHelper;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        securityHelper.doAsAdmin(() -> {
            FindClientRequest findClientRequest = new FindClientRequest();
            findClientRequest.setEmail(CLIENT_TEST_EMAIL);
            CreditTariff createdCreditTariff;
            createdCreditTariff = creditTariffService.createOrModify(createTestCreditTariffRequest());
            Client createdClient = clientService.findClient(findClientRequest)
                    .orElseGet(() -> clientService.create(createTestClientRequest()));
            log.info("Created test client: {}", createdClient);

            FindOneAccountRequest findOneAccountRequest = new FindOneAccountRequest();
            findOneAccountRequest.setAccountNumber(TEST_NUMBER);
            Account createdAccount = accountService.findAccount(findOneAccountRequest)
                    .orElseGet(() -> accountService.create(createAccountRequest(createdClient, createdCreditTariff)));
            log.info("Created test account: {}", createdAccount);
            return null;

        });
    }

    private static ModifyOrCreateCreditTariffRequest createTestCreditTariffRequest() {
        ModifyOrCreateCreditTariffRequest request = new ModifyOrCreateCreditTariffRequest();
        request.setName(CREDIT_TARIFF_TEST_NAME);
        request.setLimit(CREDIT_TARIFF_TEST_LIMIT);
        request.setPercent(CREDIT_TARIFF_TEST_PERCENT);
        return request;
    }

    private static CreateClientRequest createTestClientRequest() {
        CreateClientRequest clientForTest = new CreateClientRequest();
        clientForTest.setName(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setSurname(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setEmail(CLIENT_TEST_EMAIL);
        return clientForTest;
    }

    private static CreateAccountRequest createAccountRequest(Client owner, CreditTariff creditTariff) {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setNumber(TEST_NUMBER);
        request.setPin(TEST_PIN);
        request.setOwnerId(owner.getId());
        request.setCreditTariffId(creditTariff.getId());
        return request;
    }
}
