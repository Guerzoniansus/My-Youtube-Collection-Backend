package com.myytcollection.controller;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private static class ControllerChild extends Controller {
        // This is because Controller is an abstract class
    }

    @Test
    public void testGetJWT_WithValidHeader() {
        Controller controller = new ControllerChild();
        String validAuthorizationHeader = "Bearer 12345";

        String result = controller.getJWT(validAuthorizationHeader);

        assertEquals("12345", result);
    }

    @Test
    public void testGetJWT_WithInvalidHeader() {
        Controller controller = new ControllerChild();
        String invalidAuthorizationHeader = "InvalidHeader";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.getJWT(invalidAuthorizationHeader));

        assertEquals("Something was wrong with the authorization header.", exception.getMessage());
    }

    @Test
    public void testGetJWT_WithNullHeader() {
        Controller controller = new ControllerChild();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.getJWT(null));

        assertEquals("Something was wrong with the authorization header.", exception.getMessage());
    }

}