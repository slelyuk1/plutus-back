//package com.plutus.system.service;
//
//import com.plutus.system.configuration.security.AdminConfiguration;
//import com.plutus.system.configuration.security.JwtTokenConfiguration;
//import com.plutus.system.model.SecurityRole;
//import com.plutus.system.model.entity.Account;
//import com.plutus.system.model.entity.employee.EmployeeRole;
//import com.plutus.system.repository.AccountRepository;
//import com.plutus.system.repository.EmployeeRepository;
//import com.plutus.system.utils.AccountUtils;
//import com.plutus.system.utils.ClientUtils;
//import com.plutus.system.utils.EmployeeUtils;
//import com.plutus.system.utils.RepositorySearchUtils;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.domain.Example;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigInteger;
//import java.util.Collections;
//import java.util.Optional;
//
//// TODO: 11/7/2020 Remove constants usage
//@SpringBootTest
//@ActiveProfiles("dev")
//public class DefaultAuthorizationServiceTest {
//
//    private static final EmployeeRole TEST_EMPLOYEE_ROLE = EmployeeRole.ADMIN;
//
//    @Autowired
//    private AuthorizationService authorizationService;
//    @MockBean
//    private EmployeeRepository employeeRepository;
//    @MockBean
//    private AccountRepository accountRepository;
//
////    @TestConfiguration
////    static class DefaultAuthorizationServiceTestContextConfiguration {
////        @Primary
////        @Bean
////        AdminConfiguration adminConfiguration() {
////            return new AdminConfiguration(EmployeeUtils.TEST_LOGIN, EmployeeUtils.TEST_PASSWORD);
////        }
////
////        @Primary
////        @Bean
////        JwtTokenConfiguration jwtTokenConfiguration() {
////            return new JwtTokenConfiguration("secret", 60000);
////        }
////    }
//
//
//    @Test
//    void testSuccessfulAccountAuthorization() {
//        Account accountToAuthorize = AccountUtils.instantiateTestAccount(ClientUtils.instantiateTestClient());
//        accountToAuthorize.setId(BigInteger.ONE);
//        Mockito.when(accountRepository.findOne(Example.of(RepositorySearchUtils.accountForSearchByNumber(AccountUtils.TEST_NUMBER))))
//                .thenReturn(Optional.of(accountToAuthorize));
//        Authentication toAuthorize = new UsernamePasswordAuthenticationToken(accountToAuthorize.getNumber(), accountToAuthorize.getPin());
//        Authentication authorized = authorizationService.generateAuthorizationToken(toAuthorize);
//        Assertions.assertThat(authorized.isAuthenticated()).isTrue();
//        Assertions.assertThat(authorized.getPrincipal()).isEqualTo(accountToAuthorize.getId());
//        Assertions.assertThat(authorized.getAuthorities()).isEqualTo(Collections.singleton(SecurityRole.ATM.getGrantedAuthority()));
//    }
//
//
//}
