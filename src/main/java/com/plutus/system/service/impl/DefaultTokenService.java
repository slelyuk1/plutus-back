package com.plutus.system.service.impl;

import com.plutus.system.configuration.security.JwtTokenConfiguration;
import com.plutus.system.model.SecurityRole;
import com.plutus.system.service.JwtTokenService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultTokenService implements JwtTokenService {
    public static final String ROLES_CLAIM_NAME = "roles";

    private final String jwtSecret;
    private final long lifeInMs;

    public DefaultTokenService(JwtTokenConfiguration configuration) {
        jwtSecret = configuration.getSecret();
        lifeInMs = configuration.getLifeInMs();
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.warn(String.format("Invalid JWT signature: %s", authToken));
        } catch (MalformedJwtException ex) {
            log.warn(String.format("Invalid JWT token: %s", authToken));
        } catch (ExpiredJwtException ex) {
            log.warn(String.format("Expired JWT token: %s", authToken));
        } catch (UnsupportedJwtException ex) {
            log.warn(String.format("Unsupported JWT token: %s", authToken));
        } catch (IllegalArgumentException ex) {
            log.warn(String.format("JWT claims string is empty: %s", authToken));
        }
        return false;
    }

    @Override
    public Optional<String> getTokenFromRequest(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Authentication> getAuthenticationFromToken(@NotEmpty String token) {
        Authentication authentication = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            Number id = new BigInteger(claims.getSubject());
            @SuppressWarnings("unchecked")
            List<String> securityRolesStr = claims.get(ROLES_CLAIM_NAME, List.class);
            securityRolesStr = securityRolesStr == null ? Collections.emptyList() : securityRolesStr;
            authentication = new UsernamePasswordAuthenticationToken(id, null, grantedAuthoritiesFromStrings(securityRolesStr));
        } catch (JwtException e) {
            log.warn("Exception when parsing token occurred: ", e);
        }
        return Optional.ofNullable(authentication);
    }

    @Override
    public String getTokenFromAuthentication(@NonNull Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof Number)) {
            throw new IllegalArgumentException("Authentication must contain Number as principal!");
        }
        Number id = (Number) authentication.getPrincipal();

        Date currentDate = new Date();
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + lifeInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim(ROLES_CLAIM_NAME, grantedAuthoritiesToStrings(authentication.getAuthorities()))
                .compact();
    }

    public static Collection<String> grantedAuthoritiesToStrings(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static Collection<? extends GrantedAuthority> grantedAuthoritiesFromStrings(Collection<String> securityRolesStr) {
        return securityRolesStr.stream()
                .map(SecurityRole::fromString)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SecurityRole::getGrantedAuthority)
                .collect(Collectors.toList());
    }
}
