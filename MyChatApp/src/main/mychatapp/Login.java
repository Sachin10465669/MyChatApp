/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mychatapp;
/*
 * This Java project was developed with assistance from ChatGPT (OpenAI, 2024).
 * Tool used: https://chat.openai.com/
 * Accessed: 14 April 2025
 */
public class Login {
    private String username;
    private String password;
    private String phoneNumber;

    // Constructor
    public Login(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Method to validate the username
    public boolean isUsernameValid() {
        return this.username != null && this.username.contains("_") && this.username.length() <= 5;
    }

    // Method to validate password complexity
    public boolean isPasswordComplex() {
        return this.password != null && this.password.length() >= 8
                && this.password.matches(".*[A-Z].*") 
                && this.password.matches(".*[0-9].*") 
                && this.password.matches(".*[^a-zA-Z0-9].*");
    }

    // Method to validate phone number
    public boolean isPhoneNumberValid() {
        return this.phoneNumber != null && this.phoneNumber.matches("^\\+27\\d{9}$");
    }

    // Register User method (used for test feedback)
    public String registerUser() {
        if (!isUsernameValid()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!isPasswordComplex()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!isPhoneNumberValid()) {
            return "Cell phone number is incorrectly formatted or does not contain international code.";
        }
        return "Registration successful.";
    }

    // Login User method
    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Getter methods
    public String getUsername() {
        return username;
    }
}