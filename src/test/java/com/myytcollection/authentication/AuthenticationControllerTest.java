package com.myytcollection.authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.myytcollection.authentication.AuthenticationController;
import com.myytcollection.authentication.LoginService;
import com.myytcollection.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthenticationControllerTest {

    private AuthenticationController authenticationController;
    private LoginService loginService;

    @Before
    public void setUp() {
        loginService = mock(LoginService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        authenticationController = new AuthenticationController(loginService, jwtUtil, "yourClientId");
    }

    @Test
    public void testLoginWhenLoginServiceReturnsNull() {
        String idToken = "googleIdToken";
        when(loginService.login(eq(idToken), any(), any())).thenReturn(null);

        ResponseEntity<String> responseEntity = authenticationController.login(idToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid Google ID token.", responseEntity.getBody());
    }

    @Test
    public void testLoginWhenLoginServiceReturnsNonNull() {
        String idToken = "googleIdToken";
        when(loginService.login(eq(idToken), any(), any())).thenReturn("jwt");

        ResponseEntity<String> responseEntity = authenticationController.login(idToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("jwt", responseEntity.getBody());
    }
}
