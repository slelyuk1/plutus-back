package com.plutus.system.model.entity;

import com.plutus.system.repository.UserRepository;
import com.plutus.system.utils.ConstraintTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserEntityTest {
    private static final String TEST_NAME = "Test";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_INVALID_EMAIL = "test@test.com";

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    public void testSuccessfulClientCreation() {
        Client toCreate = new Client();
        toCreate.setName(TEST_NAME);
        toCreate.setSurname(TEST_NAME);
        toCreate.setEmail(TEST_EMAIL);
        toCreate = entityManager.persist(toCreate);
        entityManager.flush();

        Client created = repository.getOne(toCreate.getId());
        // TODO: 10/6/2020 test equals method
        assertThat(created.equals(toCreate)).isTrue();
    }

    @Test
    public void testAccountCreationWithEmptyNameOrSurname() {
        ConstraintViolationException nameException = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = new Client();
            toCreate.setName("");
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintTestUtils.hasSpecifiedConstraintViolation(nameException, "name")).isTrue();


        ConstraintViolationException surnameException = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = new Client();
            toCreate.setSurname("");
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintTestUtils.hasSpecifiedConstraintViolation(surnameException, "surname")).isTrue();
    }

    @Test
    public void testAccountCreationWithInvalidEmail() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = new Client();
            toCreate.setName(TEST_NAME);
            toCreate.setSurname(TEST_NAME);
            toCreate.setEmail("TEST_INVALID_EMAIL");
            entityManager.persist(toCreate);
            entityManager.flush();
        });

        assertThat(ConstraintTestUtils.hasSpecifiedConstraintViolation(e, "email")).isTrue();
    }
}
