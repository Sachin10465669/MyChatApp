/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * This Java project was developed with assistance from ChatGPT (OpenAI, 2024).
 * Tool used: https://chat.openai.com/
 * Accessed: 14 April 2025
 */

package mychatapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Prompt user for input
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt user for input
            System.out.println("Welcome to MyChatApp!");
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();
            
            // Create a new Login object
            Login user = new Login(username, password, phoneNumber);
            
            // Register user and get feedback
            String registrationStatus = user.registerUser();
            System.out.println(registrationStatus);
            
            if (registrationStatus.equals("Registration successful.")) {
                // If registration is successful, try logging in
                System.out.println("\nNow let's try to log in.");
                System.out.print("Enter your username for login: ");
                String loginUsername = scanner.nextLine();
                
                System.out.print("Enter your password for login: ");
                String loginPassword = scanner.nextLine();
                
                // Attempt to login
                if (user.loginUser(loginUsername, loginPassword)) {
                    System.out.println("Login successful. Welcome " + loginUsername + "!");
                } else {
                    System.out.println("Login failed. Incorrect username or password.");
                }
            }
        }
    }
}