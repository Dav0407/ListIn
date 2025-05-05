package com.igriss.ListIn.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
        String extractUsername(String token);
        <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
        String generateToken(UserDetails userDetails);
        String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
        String generateRefreshToken(UserDetails userDetails);
        boolean isTokenValid(String token, UserDetails userDetails);
        boolean isRefreshToken(String token);
        boolean isValidRefreshToken(String token, UserDetails userDetails);
        boolean isAccessTokenValid(String token, UserDetails userDetails);
        void blackListToken(String token);
        boolean validateToken(String token);
}
