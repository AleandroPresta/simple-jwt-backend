package com.auth.jwt.backend.config;

import org.springframework.stereotype.Component;

import com.auth.jwt.backend.user.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.Date;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto dto) {
        Date now = new Date(); // current date and time
        Date validity = new Date(now.getTime() + 3600000); // 1 hour from now
        return JWT.create()
            .withIssuer(dto.getLogin())
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withClaim("firstName", dto.getFirstName())
            .withClaim("lastName", dto.getLastName())
            .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier =  JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto.builder()
            .login(decoded.getIssuer())
            .firstName(decoded.getClaim("firstName").asString())
            .lastName(decoded.getClaim("lastName").asString())
            .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
