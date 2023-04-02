package org.gantry.apiserver.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JWTUtil {

    private final JWTProperties properties;

    public String create(User user) {
        JWTCreator.Builder builder = JWT.create()
                .withIssuer("gantry-server")
                .withSubject("user-authentication-token")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(properties.getExpirationSecond()))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail());
        if (user.getAuthority() != null) {
            builder.withClaim("authority", user.getAuthority().toString());
        }
        return builder.sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(properties.getSecret());
    }

    public User decode(String token) {
        DecodedJWT decoded = JWT.decode(token);
        User user = new User();

        Claim id = decoded.getClaim("id");
        if (!id.isMissing()) user.setId(id.asLong());

        Claim username = decoded.getClaim("username");
        if (!username.isMissing()) user.setUsername(username.asString());

        Claim email = decoded.getClaim("email");
        if (!email.isMissing()) user.setEmail(email.asString());

        Claim authority = decoded.getClaim("authority");
        if (!authority.isMissing() && Arrays.asList(Authority.values()).contains(authority)) {
            user.setAuthority(Authority.valueOf(authority.asString()));
        }

        return user;
    }

    public boolean verify(String token) {
        try {
            JWT.require(getAlgorithm()).build().verify(token);
            return true;

        } catch(JWTVerificationException e) {
            return false;
        }
    }
}
