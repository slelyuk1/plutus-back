package com.plutus.system.service;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.model.entity.employee.EmployeeRole;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.EmployeeRepository;
import com.plutus.system.service.impl.DefaultUserDetailsService;
import com.plutus.system.utils.AccountUtils;
import com.plutus.system.utils.ClientUtils;
import com.plutus.system.utils.EmployeeUtils;
import com.plutus.system.utils.RepositorySearchUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DefaultUserDetailsServiceTest {

    private static final Random RANDOM = new Random();

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    private UserDetailsService service;

    @BeforeEach
    void initDetailsService() {
        service = new DefaultUserDetailsService(accountRepository, employeeRepository);
    }

    @Test
    public void testSuccessfulUserDetailsRetrievalForAccount() {
        Account account = AccountUtils.instantiateTestAccount(ClientUtils.instantiateTestClient());
        account.setId(BigInteger.valueOf(RANDOM.nextInt()));
        Mockito.when(accountRepository.findOne(Example.of(RepositorySearchUtils.accountForSearchByNumber(account.getNumber()))))
                .thenReturn(Optional.of(account));
        UserDetails details = service.loadUserByUsername(account.getNumber());

        assertThat(new BigInteger(details.getUsername())).as("UserDetails username must be equal to account id")
                .isEqualTo(account.getId());

        assertThat(details.getPassword()).as("UserDetails password must be equal to account pin")
                .isEqualTo(account.getPin());

        assertThat(details.getAuthorities()).as("UserDetails must have only one authority which is equal to one contained in ATM SecurityRole")
                .hasOnlyOneElementSatisfying(
                        authority -> assertThat(authority).isEqualTo(SecurityRole.ATM.getGrantedAuthority())
                );
    }

    @Test
    public void testSuccessfulUserDetailsRetrievalForEmployee() {
        Employee employee = EmployeeUtils.instantiateTestEmployee(EmployeeRole.ADMIN);
        employee.setId(BigInteger.valueOf(RANDOM.nextInt()));
        Mockito.when(employeeRepository.findOne(Example.of(RepositorySearchUtils.employeeForSearchByLogin(employee.getLogin()))))
                .thenReturn(Optional.of(employee));
        UserDetails details = service.loadUserByUsername(employee.getLogin());

        assertThat(new BigInteger(details.getUsername())).as("UserDetails username must be equal to employee id")
                .isEqualTo(employee.getId());

        assertThat(details.getPassword()).as("UserDetails password must be equal to employee password")
                .isEqualTo(employee.getPassword());

        assertThat(details.getAuthorities()).as("UserDetails must have only one authority which is equal to one contained in ATM SecurityRole")
                .hasOnlyOneElementSatisfying(
                        authority -> assertThat(authority).isEqualTo(employee.getRole().getSecurityRole().getGrantedAuthority())
                );
    }

    @Test
    public void testInvalidArgumentToLoadUserByUsernameWith() {
        Mockito.when(accountRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.findOne(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(Mockito.any()));
    }

}
