package com.myytcollection.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.LoginService;
import com.myytcollection.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

/**
 * Class used for logging in the user.
 */
@RestController
public class LoginController {

    private final String googleClientId;
    private final LoginService loginService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /**
     * Class used for logging in the user.
     * @param loginService Service to log in the user.
     * @param jwtUtil The class used for jwt utilities.
     * @param googleClientId The google API client ID.
     * @param userRepository Class used for database access.
     */
    public LoginController(LoginService loginService, JwtUtil jwtUtil, @Value("${googleClientId}") String googleClientId,
                           UserRepository userRepository) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
        this.googleClientId = googleClientId;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint for logging in the user via their google ID token. Registers new users to the database as well.
     * @param googleIdToken The token gotten from the google API that the frontend sends. Includes the user's email.
     * @return Sends a JWT token to the frontend that includes the user's email that can be used to
     * authenticate the user. If the google API could not verify the user, a BadRequest will be sent instead.
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String googleIdToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(googleClientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        String response = loginService.login(googleIdToken, verifier, jwtUtil, userRepository);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body("Invalid Google ID token.");
    }

}
