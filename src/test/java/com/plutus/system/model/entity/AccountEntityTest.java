package com.plutus.system.model.entity;

import com.plutus.system.repository.AccountRepository;
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
    private AccountRepository repository;

    @Test
    public void testSuccessfulAccountEntryCreation() {
        Client testClient = ClientUtils.createTestClientForPersistence();
        entityManager.persist(testClient);
        Account persisted = entityManager.persist(AccountUtils.createTestAccountForPersistence(testClient));
        entityManager.flush();

        Account fromDatabase = repository.getOne(persisted.getId());
        assertThat(fromDatabase).isEqualTo(persisted);
    }

    @Test
    public void testAccountCreationWithNullPin() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client testClient = ClientUtils.createTestClientForPersistence();
            entityManager.persist(testClient);
            Account toCreate = new Account();
            toCreate.setOwner(testClient);
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(e, "pin")).isTrue();
    }

}
