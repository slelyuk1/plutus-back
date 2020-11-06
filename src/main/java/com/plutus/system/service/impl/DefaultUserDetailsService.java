package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.EmployeeRepository;
import com.plutus.system.utils.RepositorySearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Primary
@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return accountRepository.findOne(Example.of(RepositorySearchUtils.accountForSearchByNumber(id)))
                .map(DefaultUserDetailsService::accountToUserDetails)
                .orElseGet(() ->
                        employeeRepository.findOne(Example.of(RepositorySearchUtils.employeeForSearchByLogin(id)))
                                .map(DefaultUserDetailsService::employeeToUserDetails)
                                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find account or employee with username: " + id))
                );
    }

    private static UserDetails accountToUserDetails(Account account) {
        return new User(account.getId().toString(), account.getPin(), Collections.singleton(SecurityRole.ATM.getGrantedAuthority()));
    }

    private static UserDetails employeeToUserDetails(Employee employee) {
        Collection<GrantedAuthority> grantedAuthorities = Collections.singleton(employee.getRole().getSecurityRole().getGrantedAuthority());
        return new User(employee.getId().toString(), employee.getPassword(), grantedAuthorities);
    }
}
