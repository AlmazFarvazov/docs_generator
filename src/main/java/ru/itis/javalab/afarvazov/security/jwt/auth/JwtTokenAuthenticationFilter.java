package ru.itis.javalab.afarvazov.security.jwt.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.javalab.afarvazov.redis.services.BlackListService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private BlackListService blackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("X-TOKEN");
        if (token != null) {
            if (blackListService.exists(token)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            JwtTokenAuthentication tokenAuthentication = new JwtTokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
