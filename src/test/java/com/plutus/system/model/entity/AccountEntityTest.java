package com.plutus.system.model.entity;

import com.plutus.system.repository.AccountRepository;
import com.plutus.system.utils.ConstraintTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountEntityTest {
    private static final String TEST_PIN = "1234";

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountRepository repository;

    @Test
    public void testSuccessfulAccountEntryCreation() {
        Account toCreate = new Account();
        toCreate.setPin(TEST_PIN);
        toCreate = entityManager.persist(toCreate);
        entityManager.flush();

        Account created = repository.getOne(toCreate.getId());
        assertThat(created).isEqualTo(toCreate);
    }

    @Test
    public void testAccountCreationWithNullPin() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Account toCreate = new Account();
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintTestUtils.hasSpecifiedConstraintViolation(e, "pin")).isTrue();
    }

}
