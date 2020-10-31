package com.plutus.system.service.impl;

import com.plutus.system.configuration.security.JwtTokenConfiguration;
import com.plutus.system.model.SecurityRole;
import com.plutus.system.service.JwtTokenService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultTokenService implements JwtTokenService {
    private static final String ROLES_CLAIM_NAME = "roles";

    private final String jwtSecret;
    private final long lifeInMs;

    public DefaultTokenService(JwtTokenConfiguration configuration) {
        jwtSecret = configuration.getSecret();
        lifeInMs = configuration.getLifeInMs();
    }

    // TODO: 10/18/2020
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
    public Optional<String> getTokenFromRequest(@NotNull HttpServletRequest request) {
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
            long id = Long.parseLong(claims.getSubject());
            @SuppressWarnings("unchecked")
            List<String> securityRolesStr = claims.get(ROLES_CLAIM_NAME, List.class);
            securityRolesStr = securityRolesStr == null ? Collections.emptyList() : securityRolesStr;
            Collection<GrantedAuthority> grantedAuthorities = securityRolesStr.stream()
                    .map(SecurityRole::fromString)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(SecurityRole::getGrantedAuthority)
                    .collect(Collectors.toList());
            authentication = new UsernamePasswordAuthenticationToken(id, token, grantedAuthorities);
        } catch (JwtException e) {
            log.warn("Exception when parsing token occurred: ", e);
        }
        return Optional.ofNullable(authentication);
    }

    @Override
    public String getTokenFromAuthentication(@NotNull Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date expiryDate = new Date(new Date().getTime() + lifeInMs);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim(ROLES_CLAIM_NAME, roles)
                .compact();
    }
}
