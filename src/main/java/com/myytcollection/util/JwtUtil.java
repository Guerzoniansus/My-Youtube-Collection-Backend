package com.myytcollection.util;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Class used for JWT (Javascript WebToken) utilities.
 */
@Component
public class JwtUtil {

    private final String key;

    /**
     * Class used for JWT (Javascript WebToken) utilities.
     * @param key A secret key used for generating JWTs.
     */
    public JwtUtil(@Value("${jwtSecretKey}") String key){
        this.key = key;
    }

    /**
     * Generate a JWT based on a user's email.
     * @param email The email to include in the JWT.
     * @return A JWT token that can be used in the frontend for authentication.
     */
    public String generateJwt(String email) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * Extracts the email from a JWT token.
     * @param jwt The JWT to extract the email from.
     * @return The user's email.
     */
    public String extractEmailFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
