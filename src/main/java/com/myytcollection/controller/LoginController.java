package com.myytcollection.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.UserService;
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

    private final UserService userService;

    /**
     * Class used for logging in the user.
     * @param userService Service to log in the user.
     */
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for logging in the user via their google ID token. Registers new users to the database as well.
     * @param googleIdToken The token gotten from the google API that the frontend sends. Includes the user's email.
     * @return Sends a JWT token to the frontend that includes the user's email that can be used to
     * authenticate the user. If the google API could not verify the user, a BadRequest will be sent instead.
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String googleIdToken) {
        String response = userService.login(googleIdToken);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body("Invalid Google ID token.");
    }
}
