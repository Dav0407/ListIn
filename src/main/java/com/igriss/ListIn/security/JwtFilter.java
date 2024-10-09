package com.igriss.ListIn.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


/**
 * The JwtFilter class is a filter that handles JWT authentication in a web application.
 * It extends the OncePerRequestFilter class and overrides the doFilterInternal() method
 * to perform the necessary authentication logic.

 * This filter checks the Authorization header of the incoming request for a JWT token. If a valid token
 * is found, it extracts the email from the token and validates it*/
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter
{
    private final JwtServices jwtServices;

    public static Boolean CHECK=false;

    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        String authHeader=null;
        String token = null;
        String email = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("_jwt".equals(cookie.getName())) {
                    request.setAttribute(AUTHORIZATION, "Bearer " + cookie.getValue());
                    authHeader=request.getAttribute(AUTHORIZATION).toString();
                }
            }
        }

        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            if (CHECK) log.info("Bearer token: {}",token);
            CHECK=false;
            email=jwtServices.extractEmail(token);
        }
        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails details=context.getBean(UserDetailsService.class).loadUserByUsername(email);

            if (jwtServices.validateToken(token,details)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(details,null ,details.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }

}
