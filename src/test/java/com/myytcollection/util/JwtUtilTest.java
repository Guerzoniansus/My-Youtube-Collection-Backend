package com.myytcollection.util;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final String SECRET_KEY = "B37E9E867E3518B5F0BD1756FB3D9ECDFC18692132668A481322BBF50636CFB7";

    @Test
    void generateJwt() {
        JwtUtil jwtUtil = new JwtUtil(SECRET_KEY);
        String email = "weezimonkey@gmail.com";
        String jwt = jwtUtil.generateJwt("weezimonkey@gmail.com");

        assertEquals(email, jwtUtil.extractEmailFromJwt(jwt));
    }

    @Test
    void extractEmailFromJwt() {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3ZWV6aW1vbmtleUBnbWFpbC5jb20iLCJpYXQiOjE2OTc3MTMwNDl9.5DcMwf5K7EPSjScoqvCbzU4-wVXk79vYAdjbJRJLZxW5LDB4RPzU922Prb9T1a0vslk4hEcds1RmyqZkwIlfdw";
        JwtUtil jwtUtil = new JwtUtil(SECRET_KEY);

        String expected = "weezimonkey@gmail.com";
        String actual = jwtUtil.extractEmailFromJwt(jwt);

        assertEquals(expected, actual);
    }
}