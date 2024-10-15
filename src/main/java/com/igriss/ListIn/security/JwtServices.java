package com.igriss.ListIn.security;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * The JwtServices class provides methods for generating and validating JWT tokens.
 * It utilizes the JJWT library for JWT token processing.

 * Example usage:

 * JwtServices jwtService = new JwtServices();
 * String token = jwtService.generateToken("username");
 * String email = jwtService.extractEmail(token);
 * boolean isValid = jwtService.validateToken(token, userDetails);
 */
@Service
@Slf4j
public class JwtServices {

    private static String SECRET_KEY="";
    public JwtServices() {
        try{
            KeyGenerator generator=KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey=generator.generateKey();
            SECRET_KEY= Base64.getMimeEncoder().encodeToString(secretKey.getEncoded());
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("No Such Algorithm Exception");
        }
    }
    public String generateToken(String username){
            return createToken(username);
    }

    private String createToken(String username){
              return   Jwts
                .builder()
                .claims()
                .add(new HashMap<>())
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*1000))
                .and()
                .signWith(getKey())
                .compact();
    }


    public String extractEmail(String token) {
        return extractClaims(token,Claims::getSubject);
    }
    private SecretKey getKey(){
        byte []bytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        try {
            return claimsResolver.apply(claims);
        }catch (Exception e){
              log.warn(e.toString());
              return null;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }catch (Exception e){
        return null;
    }
    }

    public boolean validateToken(String token, UserDetails userDetails) {

             final String email = extractEmail(token);
            return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));

       }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


}
