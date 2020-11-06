//package com.plutus.system.service;
//
//import com.plutus.system.model.SecurityRole;
//import com.plutus.system.model.entity.Account;
//import com.plutus.system.model.entity.Client;
//import com.plutus.system.model.entity.employee.Employee;
//import com.plutus.system.model.entity.employee.EmployeeRole;
//import com.plutus.system.utils.AccountUtils;
//import com.plutus.system.utils.ClientUtils;
//import com.plutus.system.utils.EmployeeUtils;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Transactional
//@AutoConfigureCache
//@AutoConfigureDataJpa
//@AutoConfigureTestDatabase
//@AutoConfigureTestEntityManager
//@SpringBootTest()
//public class DefaultAuthorizationServiceTest {
//
//    private static final EmployeeRole TEST_EMPLOYEE_ROLE = EmployeeRole.ADMIN;
//
//    @Autowired
//    private TestEntityManager entityManager;
//    @Autowired
//    private AuthorizationService authorizationService;
//
//    private Account createdAccount;
//    private Employee createdEmployee;
//
//    @BeforeEach
//    void populateDb() {
//        Client createdClient = entityManager.persist(ClientUtils.instantiateTestClient());
//        createdAccount = entityManager.persist(AccountUtils.instantiateTestAccount(createdClient));
//        createdEmployee = entityManager.persist(EmployeeUtils.instantiateTestEmployee(TEST_EMPLOYEE_ROLE));
//    }
//
//    @Test
//    void testSuccessfulAccountAuthorization() {
//        Authentication toAuthorize = new UsernamePasswordAuthenticationToken(AccountUtils.TEST_NUMBER, AccountUtils.TEST_PIN);
//        Authentication authorized = authorizationService.generateAuthorizationToken(toAuthorize);
//        Assertions.assertThat(authorized.isAuthenticated()).isTrue();
//        Assertions.assertThat(authorized.getPrincipal()).isEqualTo(createdAccount.getId());
//        Assertions.assertThat(authorized.getAuthorities()).isEqualTo(Collections.singleton(SecurityRole.ATM.getGrantedAuthority()));
//    }
//
//
//}
