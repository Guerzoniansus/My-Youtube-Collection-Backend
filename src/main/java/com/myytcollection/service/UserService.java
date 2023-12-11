package com.myytcollection.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.myytcollection.model.User;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private GoogleIdTokenVerifier verifier;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository, @Value("${googleAPIClientId}") String googleAPIClientId) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(googleAPIClientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
    }

    /**
     * Generates a JWT token used for authentication, and registers the user in the database if needed.
     * @param googleIdToken The Google ID token received from the frontend to identify the user.
     * @return A JWT with the user's email if a valid google Id token was given, otherwise null.
     */
    public String login(String googleIdToken) {
        String email = verifyGoogleIdToken(googleIdToken);

        if (email == null) {
            return null;
        }

        User user = new User(email);
        userRepository.save(user);

        return jwtUtil.generateJwt(email);
    }

    /**
     * Gets a user from the database.
     * @param jwt The JWT token used to identify the user.
     * @return A user object.
     */
    public User getUser(String jwt) {
        try {
            String email = getEmail(jwt);

            if (userRepository.findById(email).isPresent()) {
                return userRepository.findById(email).get();
            }
            else throw new NoSuchElementException("Could not identify the user.");
        } catch (Exception e) {
            throw new NoSuchElementException("Could not identify the user.");
        }
    }

    /**
     * Gets the user's email from the request's authorization header.
     * @param jwt The JWT token from the frontend that should contain the user's email.
     * @return The user's email.
     */
    private String getEmail(String jwt) {
        return jwtUtil.extractEmailFromJwt(jwt);
    }

    /**
     * Verifies a google user Id token.
     * @param googleIdToken The token to verify.
     * @return The user's email if the token is valid, otherwise null.
     */
    private String verifyGoogleIdToken(String googleIdToken) {
        GoogleIdToken idToken = null;

        try {
            idToken = verifier.verify(googleIdToken);
        } catch (Exception e) {
            return null;
        }

        if (idToken == null) {
            return null;
        }

        Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        return email;
    }

    /**
     * Sets the Google Id Token verifier for this class, used for authenticating the user.
     * When this method isn't used, then a default value will be used.
     * This method mainly exists for unit testing purposes.
     * @param verifier The verifier to use.
     */
    void setVerifier(GoogleIdTokenVerifier verifier) {
        this.verifier = verifier;
    }
}
