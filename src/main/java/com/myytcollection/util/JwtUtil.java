package com.myytcollection.util;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String key;

    public JwtUtil(@Value("${jwtSecretKey}") String key){
        this.key = key;
    }

    public String generateJwt(String email) {
        Date now = new Date();
        System.out.println("Using key: " + key);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String extractEmailFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
