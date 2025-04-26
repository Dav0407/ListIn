package com.igriss.ListIn.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final RedisTemplate<String, Object> redisTemplate;

    //decryption phrase for secret_key = Hello, hello my name is Igris004
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "access");
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "refresh");
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return buildToken(claims, userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){

        if (isTokenBlacklisted(token)){
            return false;
        }
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenNotExpired(token);
    }

    // by doing so, we are ensuring that user is not trying to access with refresh token
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        if (isTokenBlacklisted(token)) {
            return false;
        }
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenNotExpired(token) && !isRefreshToken(token);
    }

    private boolean isTokenNotExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    public boolean isRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get("token_type").toString();
            return "refresh".equals(tokenType);
        } catch (Exception e) {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Tokenni blacklistga qo'yish
    @Override
    public void blackListToken(String token){
        long expiration = extractExpiration(token).getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
    }

    //Agar token blacklistga tushgan bo'lsa
    private boolean isTokenBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    //checking if the requesting  token is refresh token
    public boolean isValidRefreshToken(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails) && isRefreshToken(token);
    }

    @Override
    public boolean validateToken(String token) {
        if (isTokenBlacklisted(token)) {
            return false;
        }
        try {
            return isTokenNotExpired(token) && !isRefreshToken(token);
        } catch (Exception e) {
            return false;
        }
    }

}
