package com.plutus.system.utils;

import com.plutus.system.configuration.security.AdminConfiguration;
import com.plutus.system.model.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SecurityHelper {
    private final AdminConfiguration configuration;
    private final UserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public <T> T doAsAdmin(Supplier<T> supplier) {
        Authentication auth = new UsernamePasswordAuthenticationToken(configuration.getLogin(), configuration.getPassword());
        return doAs(auth, supplier);
    }

    public <T> T doAs(Authentication auth, Supplier<T> supplier) {
        auth = authenticationManager.authenticate(auth);
        Authentication previousAuthentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(auth);
        T result = supplier.get();
        SecurityContextHolder.getContext().setAuthentication(previousAuthentication);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPrincipalFromSecurityContext() {
        return (T) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static <T> T requireRole(SecurityRole role, Supplier<T> supplier) {
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(authority -> authority.equals(role.getGrantedAuthority()))
                .findAny()
                .orElseThrow(() -> new SecurityException("SecurityContext doesn't have specified role " + role.getRoleString()));
        return supplier.get();
    }
}
