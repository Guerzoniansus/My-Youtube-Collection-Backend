package com.myytcollection.controller;

import com.myytcollection.util.JwtUtil;

public abstract class Controller {

    /**
     * Gets the user's email from the request's authorization header.
     * @param authorizationHeader The authorization header from the http request.
     * @throws IllegalArgumentException This gets thrown if there was something wrong with the header.
     * @return The user's email.
     */
    public String getJWT(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // To remove "Bearer " and extract the token
        }

        else throw new IllegalArgumentException("Something was wrong with the authorization header.");
    }
}
