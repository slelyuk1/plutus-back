package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.EmployeeRepository;
import com.plutus.system.utils.EmployeeUtils;
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
        // TODO: 11/1/2020 util
        Account toFind = new Account();
        toFind.setNumber(id);
        return accountRepository.findOne(Example.of(toFind))
                .map(DefaultUserDetailsService::accountToUserDetails)
                .orElseGet(() ->
                        employeeRepository.findOne(Example.of(EmployeeUtils.initEmployeeForSearchByLogin(id)))
                                .map(DefaultUserDetailsService::employeeToUserDetails)
                                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find account with id: " + id))
                );
    }

    private static UserDetails accountToUserDetails(Account account) {
        return new User(account.getNumber(), account.getPin(), Collections.singleton(SecurityRole.ATM.getGrantedAuthority()));
    }

    private static UserDetails employeeToUserDetails(Employee employee) {
        Collection<GrantedAuthority> grantedAuthorities = Collections.singleton(employee.getRole().getSecurityRole().getGrantedAuthority());
        return new User(employee.getId().toString(), employee.getPassword(), grantedAuthorities);
    }
}
