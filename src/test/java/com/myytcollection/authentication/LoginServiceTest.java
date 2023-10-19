package com.myytcollection.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.myytcollection.util.JwtUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private GoogleIdTokenVerifier verifier;

    @Test
    public void testLoginWithNullEmail() throws GeneralSecurityException, IOException {
        when(verifier.verify(anyString())).thenReturn(null);

        String jwtToken = loginService.login("invalidToken", verifier, jwtUtil);

        assertNull(jwtToken);
    }

    @Test
    public void testLoginWithNonNullEmail() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mock(GoogleIdToken.class);
        Payload payload = mock(Payload.class);

        when(verifier.verify(anyString())).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(payload);
        when(payload.getEmail()).thenReturn("user@example.com");
        when(jwtUtil.generateJwt("user@example.com")).thenReturn("validToken");

        String jwtToken = loginService.login("validToken", verifier, jwtUtil);

        assertEquals("validToken", jwtToken);
    }
}
