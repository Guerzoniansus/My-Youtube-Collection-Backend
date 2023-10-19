package com.myytcollection.util;

import com.google.api.client.util.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Date;

public class JwtUtil {

    private final String KEY;

    public JwtUtil(String jwtSecretKey) {
        this.KEY = jwtSecretKey;
    }

    public String generateJwt(String email) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public String extractEmailFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
