package com.myytcollection.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.myytcollection.util.JwtUtil;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class LoginService {

    /**
     * Generates a JWT token used for authentication, and registers the user in the database if needed
     * @param googleIdToken
     * @param verifier
     * @param jwtUtil
     * @return A JWT if a valid google Id token was given, otherwise null.
     */
    public String login(String googleIdToken, GoogleIdTokenVerifier verifier, JwtUtil jwtUtil) {
        String email = verifyGoogleIdToken(googleIdToken, verifier);

        if (email == null) {
            return null;
        }

        return jwtUtil.generateJwt(email);
    }

    private String verifyGoogleIdToken(String googleIdToken, GoogleIdTokenVerifier verifier) {
        GoogleIdToken idToken = null;

        try {
            idToken = verifier.verify(googleIdToken);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }

        if (idToken == null) {
            return null;
        }

        Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        return email;
    }
}
