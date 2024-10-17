package com.auth.jwt.backend.config;

import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
    
    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (header != null) {
                String[] authElements = header.split(" ");

                if (authElements.length == 2 && authElements[0].equals("Bearer")) {
                    try {
                        SecurityContextHolder .getContext().setAuthentication(userAuthProvider.validateToken(authElements[1]));
                    } catch (RuntimeException e) {
                        SecurityContextHolder.clearContext();
                        throw e;
                    }
                }   
            }

            filterChain.doFilter(request, response);
    }

}
