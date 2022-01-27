package ru.itis.javalab.afarvazov.security.jwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.javalab.afarvazov.redis.services.BlackListService;

@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final Algorithm algorithm;

    private final BlackListService blackListService;

    public JwtTokenAuthenticationProvider(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
                                          Algorithm algorithm, BlackListService blackListService) {
        this.userDetailsService = userDetailsService;
        this.algorithm = algorithm;
        this.blackListService = blackListService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthentication tokenAuthentication = (JwtTokenAuthentication) authentication;
        DecodedJWT jwt;
        try {
            jwt = JWT.require(algorithm)
                    .build()
                    .verify(authentication.getName());
        } catch (TokenExpiredException e) {
            blackListService.add(authentication.getName());
            throw new CredentialsExpiredException("Token expired");
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Token is not valid");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getClaim("email").asString());
        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setUserDetails(userDetails);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtTokenAuthentication.class);
    }
}
