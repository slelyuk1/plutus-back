package com.plutus.system.service;

import com.plutus.system.configuration.security.JwtTokenConfiguration;
import com.plutus.system.model.SecurityRole;
import com.plutus.system.service.impl.DefaultTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;
import java.util.*;

import static com.plutus.system.service.impl.DefaultTokenService.ROLES_CLAIM_NAME;
import static com.plutus.system.service.impl.DefaultTokenService.grantedAuthoritiesFromStrings;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DefaultTokenServiceTest {
    private static final JwtTokenConfiguration DEFAULT_CONFIGURATION = new JwtTokenConfiguration("secret", 10000);
    private static final BigInteger DEFAULT_PRINCIPAL = BigInteger.ONE;
    private static final List<GrantedAuthority> DEFAULT_AUTHORITIES =
            Arrays.asList(SecurityRole.ADMIN.getGrantedAuthority(), SecurityRole.ATM.getGrantedAuthority());

    private JwtTokenService service;

    @BeforeEach
    public void initService() {
        service = new DefaultTokenService(DEFAULT_CONFIGURATION);
    }

    @Test
    public void testGetTokenFromAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(DEFAULT_PRINCIPAL, null, DEFAULT_AUTHORITIES);

        String jwtStr = service.getTokenFromAuthentication(authentication);
        Claims claims = Jwts.parser()
                .setSigningKey(DEFAULT_CONFIGURATION.getSecret())
                .parseClaimsJws(jwtStr)
                .getBody();
        BigInteger id = new BigInteger(claims.getSubject());
        Date created = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        @SuppressWarnings("unchecked")
        Collection<GrantedAuthority> grantedAuthorities = grantedAuthoritiesFromStrings(claims.get(ROLES_CLAIM_NAME, List.class));

        assertThat(id).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(expiration).isAfter(created);
        assertThat(new Date(expiration.getTime() - DEFAULT_CONFIGURATION.getLifeInMs())).isBeforeOrEqualTo(created);
        assertThat(grantedAuthorities).isEqualTo(DEFAULT_AUTHORITIES);
    }

    @Test
    public void testGetAuthenticationFromToken() {
        Date issuedAt = new Date();
        String jwtStr = Jwts.builder()
                .setSubject(DEFAULT_PRINCIPAL.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(new Date(issuedAt.getTime() + DEFAULT_CONFIGURATION.getLifeInMs()))
                .signWith(SignatureAlgorithm.HS512, DEFAULT_CONFIGURATION.getSecret())
                .claim(ROLES_CLAIM_NAME, DefaultTokenService.grantedAuthoritiesToStrings(DEFAULT_AUTHORITIES))
                .compact();
        Optional<Authentication> maybeAuthentication = service.getAuthenticationFromToken(jwtStr);
        assertThat(maybeAuthentication).isPresent();
        Authentication authentication = maybeAuthentication.get();
        assertThat(authentication.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(authentication.getCredentials()).isNull();
        assertThat(authentication.getAuthorities()).isEqualTo(DEFAULT_AUTHORITIES);
    }

    @Test
    public void testBidirectionalConversion() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(DEFAULT_PRINCIPAL, null, DEFAULT_AUTHORITIES);
        String jwtStr = service.getTokenFromAuthentication(authentication);
        Authentication fromJwt = service.getAuthenticationFromToken(jwtStr)
                .orElseThrow(() -> new IllegalStateException("Couldn't convert from valid jwt to authentication!"));
        assertThat(authentication).isEqualTo(fromJwt);
    }

}
