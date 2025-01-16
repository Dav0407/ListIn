package com.igriss.ListIn.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igriss.ListIn.exceptions.RateLimitExceededException;
import com.igriss.ListIn.exceptions.TokenIsBlacklistedException;
import com.igriss.ListIn.handler.ExceptionResponse;
import com.igriss.ListIn.security.service.JwtServiceImpl;
import com.igriss.ListIn.security.service.RateLimitingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.igriss.ListIn.handler.BusinessErrorCodes.*;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;
    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException, RateLimitExceededException {

        //firstly we have to get the ip address of each user
        String clientIp = request.getRemoteAddr();

        //if the number of counts more than the applicable counts, it will throw TOO_MANY_REQUESTS
        if (rateLimitingService.isRateLimitExceeded(clientIp)) {
            log.warn("Too many request made from the IP address: {}", clientIp);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(ExceptionResponse.builder()
                            .businessErrorCode(TOO_MANY_ATTEMPTS.getCode())
                            .businessErrorDescription(TOO_MANY_ATTEMPTS.getDescription())
                            .errorMessage("Too many request made from the IP address: " + clientIp)
                            .build())
            );
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validate token and check if blacklisted
                if (jwtService.isAccessTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else {
                    throw new TokenIsBlacklistedException("Your authentication token is blacklisted or expired. Please log in again.");
                }
            } catch (UsernameNotFoundException | TokenIsBlacklistedException exception) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write(
                        new ObjectMapper().writeValueAsString(ExceptionResponse.builder()
                                        .businessErrorCode(USER_UNAUTHORIZED.getCode())
                                        .businessErrorDescription(USER_UNAUTHORIZED.getDescription())
                                        .errorMessage(exception.getMessage())
                                        .build())
                );
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}

