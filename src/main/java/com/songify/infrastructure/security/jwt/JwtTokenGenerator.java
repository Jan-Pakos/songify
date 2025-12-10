package com.songify.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.songify.infrastructure.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.time.*;

@Component
@RequiredArgsConstructor
class JwtTokenGenerator {

    public static final String ROLES_CLAIM = "roles";
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties properties;
    private final KeyPair keyPair;

    public String authenticateAndGenerateToken(String username, String password) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Instant issuedAt = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAfter = issuedAt.plus(Duration.ofMinutes(properties.expirationMinutes()));

        PrivateKey privateKey = keyPair.getPrivate();

        Algorithm algorithm = Algorithm.RSA256(null,(RSAPrivateKey) privateKey);
        return JWT.create()
                .withSubject(securityUser.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAfter)
                .withIssuer(properties.issuer())
                .withClaim(ROLES_CLAIM, securityUser.getAuthoritiesAsString())
                .sign(algorithm);
    }
}
