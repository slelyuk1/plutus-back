package com.plutus.system.model.entity;

import com.plutus.system.repository.ClientRepository;
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
public class ClientEntityTest {
    private static final String TEST_INVALID_EMAIL = "test@test.com";

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClientRepository repository;

    @Test
    public void testSuccessfulClientCreation() {
        Client created = entityManager.persist(ClientUtils.instantiateTestClient());
        entityManager.flush();

        Client fromDatabase = repository.getOne(created.getId());
        // TODO: 10/6/2020 test equals method
        assertThat(created.equals(fromDatabase)).isTrue();
    }

    @Test
    public void testAccountCreationWithEmptyNameOrSurname() {
        ConstraintViolationException nameException = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = new Client();
            toCreate.setName("");
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(nameException, "name")).isTrue();


        ConstraintViolationException surnameException = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = new Client();
            toCreate.setSurname("");
            entityManager.persist(toCreate);
            entityManager.flush();
        });
        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(surnameException, "surname")).isTrue();
    }

    @Test
    public void testAccountCreationWithInvalidEmail() {
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Client toCreate = ClientUtils.instantiateTestClient();
            toCreate.setEmail("TEST_INVALID_EMAIL");
            entityManager.persist(toCreate);
            entityManager.flush();
        });

        assertThat(ConstraintUtils.hasSpecifiedConstraintViolation(e, "email")).isTrue();
    }
}
