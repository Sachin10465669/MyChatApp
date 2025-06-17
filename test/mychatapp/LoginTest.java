package mychatapp;

/*
 * This Java project was developed with assistance from ChatGPT (OpenAI, 2024).
 * Tool used: https://chat.openai.com/
 * Accessed: 14 April 2025
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testValidUsername() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.isUsernameValid());
    }

    @Test
    public void testInvalidUsername() {
        Login user = new Login("kyle", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.isUsernameValid());
    }

    @Test
    public void testValidPassword() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.isPasswordComplex());
    }

    @Test
    public void testInvalidPassword() {
        Login user = new Login("kyl_1", "password", "+27838968976");
        assertFalse(user.isPasswordComplex());
    }

    @Test
    public void testValidPhoneNumber() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.isPhoneNumberValid());
    }

    @Test
    public void testInvalidPhoneNumber() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(user.isPhoneNumberValid());
    }

    @Test
    public void testRegistrationSuccessful() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration successful.", user.registerUser());
    }

    @Test
    public void testRegistrationInvalidUsername() {
        Login user = new Login("kyle", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", user.registerUser());
    }

    @Test
    public void testRegistrationInvalidPassword() {
        Login user = new Login("kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", user.registerUser());
    }

    @Test
    public void testRegistrationInvalidPhoneNumber() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell phone number is incorrectly formatted or does not contain international code.", user.registerUser());
    }
}
