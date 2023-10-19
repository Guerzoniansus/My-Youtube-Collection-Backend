package com.myytcollection.authentication;

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
     * Checks if the ID token is valid. If so, it returns
     * a JWT token with the user' s email and registers the user in
     * the database if it's a new user. If the ID token is not valid it returns null.
     * @param googleIdToken The token from the frontend to authenticate
     * @return A JWT token used for authentication
     */
    public String login(String googleIdToken, GoogleIdTokenVerifier verifier, JwtUtil jwtUtil) {
        String email = verifyGoogleIdToken(googleIdToken, verifier);

        if (email == null) {
            return null;
        }

        return jwtUtil.generateJwt(email);
    }

    /**
     * Checks if the google ID token is valid and returns the user's email if true.
     * @param googleIdToken The google ID token.
     * @return The user's s email or null.
     */
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
