package com.myytcollection.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.myytcollection.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class AuthenticationController {

    @Autowired
    private Environment environment;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(environment.getProperty("googleClientId")))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        LoginService loginService = new LoginService(verifier);
        String response = loginService.login(idToken, new JwtUtil(environment.getProperty("jwtSecretKey")));

        if (response == null) {
            return ResponseEntity.badRequest().body("Invalid Google ID token.");
        }

        else {
            return ResponseEntity.ok(response);
        }
    }

}
