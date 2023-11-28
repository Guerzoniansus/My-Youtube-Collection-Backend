package com.myytcollection.controller;

import com.myytcollection.util.JwtUtil;

public abstract class Controller {

    /**
     * Gets the user's email from the request's authorization header.
     * @param authorizationHeader The authorization header from the request.
     * @param jwtUtil The class used for extracting the email.
     * @throws IllegalArgumentException This gets thrown if there was something wrong with the header.
     * @return The user's email.
     */
    public String getEmail(String authorizationHeader, JwtUtil jwtUtil) {
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // To remove "Bearer " and extract the token
        }

        else throw new IllegalArgumentException("Something was wrong with the authorization header");

        try {
            return jwtUtil.extractEmailFromJwt(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not extract the email from the JWT token");
        }
    }
}
