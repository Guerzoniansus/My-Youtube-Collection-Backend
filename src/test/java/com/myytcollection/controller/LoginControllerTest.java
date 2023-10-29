package com.myytcollection.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.LoginService;
import com.myytcollection.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginControllerTest {

    private LoginController loginController;
    private LoginService loginService;

    @Before
    public void setUp() {
        loginService = mock(LoginService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UserRepository userRepository = mock(UserRepository.class);
        loginController = new LoginController(loginService, jwtUtil, "yourClientId", userRepository);
    }

    @Test
    public void testLoginWhenLoginServiceReturnsNull() {
        String idToken = "googleIdToken";
        when(loginService.login(eq(idToken), any(), any(), any())).thenReturn(null);

        ResponseEntity<String> responseEntity = loginController.login(idToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid Google ID token.", responseEntity.getBody());
    }

    @Test
    public void testLoginWhenLoginServiceReturnsNonNull() {
        String idToken = "googleIdToken";
        when(loginService.login(eq(idToken), any(), any(), any())).thenReturn("jwt");

        ResponseEntity<String> responseEntity = loginController.login(idToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("jwt", responseEntity.getBody());
    }
}
