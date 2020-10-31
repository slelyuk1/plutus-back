package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary
@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return repository.findById(Long.valueOf(id))
                .map(DefaultUserDetailsService::accountToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find account with id: " + id));
    }

    private static UserDetails accountToUserDetails(Account account) {
        return new User(account.getId().toString(), account.getPin(), Collections.singleton(SecurityRole.ATM.getGrantedAuthority()));
    }
}
