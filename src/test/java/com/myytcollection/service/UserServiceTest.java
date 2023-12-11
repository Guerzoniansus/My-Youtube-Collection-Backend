package com.myytcollection.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.myytcollection.repository.UserRepository;
import com.myytcollection.util.JwtUtil;
import org.junit.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;

    private GoogleIdTokenVerifier verifier;

    @Before
    public void setup() {
        this.jwtUtil = mock(JwtUtil.class);
        this.userRepository = mock(UserRepository.class);
        this.verifier = mock(GoogleIdTokenVerifier.class);

        this.userService = new UserService(jwtUtil, userRepository, "id");
        this.userService.setVerifier(verifier);
    }

    @Test
    public void testLogin_WithNullEmail_ReturnsNull() throws GeneralSecurityException, IOException {
        when(verifier.verify(anyString())).thenReturn(null);

        String jwtToken = userService.login("invalidToken");

        assertNull(jwtToken);
    }

    @Test
    public void testLoginWithNonNullEmailReturnsJwt() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mock(GoogleIdToken.class);
        Payload payload = mock(Payload.class);

        when(verifier.verify(anyString())).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(payload);
        when(payload.getEmail()).thenReturn("user@example.com");
        when(jwtUtil.generateJwt("user@example.com")).thenReturn("validToken");

        String jwtToken = userService.login("validToken");

        assertEquals("validToken", jwtToken);
    }

    @Test
    public void testLoginWithNonNullEmailSavesUserToDatabase() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mock(GoogleIdToken.class);
        Payload payload = mock(Payload.class);

        when(verifier.verify(anyString())).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(payload);
        when(payload.getEmail()).thenReturn("user@example.com");
        when(jwtUtil.generateJwt("user@example.com")).thenReturn("validToken");

        userService.login("validToken");

        verify(userRepository).save(any());
    }

    @Test
    public void testLoginWithNullEmailDoesNotSaveUserToDatabase() throws GeneralSecurityException, IOException {
        when(verifier.verify(anyString())).thenReturn(null);

        userService.login("invalidToken");

        verifyNoInteractions(userRepository);
    }
}
