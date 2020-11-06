package com.plutus.system.model.entity;

import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.utils.AccountUtils;
import com.plutus.system.utils.ClientUtils;
import com.plutus.system.utils.ConstraintUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountEntityTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testSuccessfulAccountEntryCreation() {
        Client testClient = ClientUtils.instantiateTestClient();
        entityManager.persist(testClient);
        Account persisted = entityManager.persist(AccountUtils.instantiateTestAccount(testClient));
        entityManager.flush();

        Account fromDatabase = accountRepository.getOne(persisted.getId());
        assertThat(fromDatabase).isEqualTo(persisted);
    }

    @Test
    public void testAccountCreationWithNullPin() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client testClient = ClientUtils.instantiateTestClient();
            entityManager.persist(testClient);
            Account toCreate = new Account();
            toCreate.setOwner(testClient);
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(e, "pin")).isTrue();
    }

    @Test
    public void testAccountCreationWithoutOwner() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Account account = new Account();
            account.setPin(AccountUtils.TEST_PIN);
            entityManager.persist(account);
            entityManager.flush();
        });
        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(e, "owner")).isTrue();
    }

    @Test
    public void testRelationshipBetweenAccountAndClient() {
        Client client = ClientUtils.instantiateTestClient();
        Account account = AccountUtils.instantiateTestAccount(client);
        assertThat(client.getAccounts()).contains(account);
        client = entityManager.persist(client);
        account = entityManager.persist(account);
        entityManager.flush();

        Client clientFromDatabase = clientRepository.getOne(client.getId());
        assertThat(clientFromDatabase.getAccounts()).contains(account);
        Account accountFromDatabase = accountRepository.getOne(account.getId());
        assertThat(accountFromDatabase.getOwner()).isEqualTo(client);
    }

}
