package com.myytcollection.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.myytcollection.repository.UserRepository;
import com.myytcollection.service.UserService;
import com.myytcollection.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;


    @Test
    public void testLogin_WhenUserServiceReturnsNull() {
        String idToken = "googleIdToken";
        when(userService.login(eq(idToken))).thenReturn(null);

        ResponseEntity<String> responseEntity = loginController.login(idToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid Google ID token.", responseEntity.getBody());
    }

    @Test
    public void testLogin_WhenUserServiceReturnsNonNull() {
        String idToken = "googleIdToken";
        when(userService.login(eq(idToken))).thenReturn("jwt");

        ResponseEntity<String> responseEntity = loginController.login(idToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("jwt", responseEntity.getBody());
    }
}
