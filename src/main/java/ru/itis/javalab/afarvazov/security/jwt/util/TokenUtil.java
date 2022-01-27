package ru.itis.javalab.afarvazov.security.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.javalab.afarvazov.models.User;

import java.util.Date;

@Component
public class TokenUtil {

    private static final Long validTime = 180000l;

    private final Algorithm algorithm;

    public TokenUtil(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("email", user.getEmail())
                .withExpiresAt(getExpiresTime())
                .sign(algorithm);
    }

    private static Date getExpiresTime() {
        return new Date(new Date().getTime() + validTime);
    }

}
