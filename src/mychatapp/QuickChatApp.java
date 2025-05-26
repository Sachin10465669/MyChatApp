/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mychatapp;

/**
 *
 * @author Sachin
 */
// QuickChatApp.java
import javax.swing.JOptionPane;
import java.util.Scanner;

public class QuickChatApp {
    private boolean loggedIn = false;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        QuickChatApp app = new QuickChatApp();
        app.run();
    }

    public void run() {
        while (!loggedIn) {
            if (!login()) {
                int choice = JOptionPane.showConfirmDialog(null, 
                    "Would you like to try logging in again?", 
                    "Login Failed", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) System.exit(0);
            }
        }

        displayWelcomeMessage();

        boolean running = true;
        while (running) {
            int choice = displayMenu();
            switch (choice) {
                case 1 -> sendMessages();
                case 2 -> showRecentMessages();
                case 3 -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat!");
                }
            }
        }
    }

    private boolean login() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");

        if (username != null && !username.trim().isEmpty() && 
            password != null && !password.trim().isEmpty()) {
            loggedIn = true;
            JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username + "!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
            return false;
        }
    }

    private void displayWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.", "QuickChat", JOptionPane.INFORMATION_MESSAGE);
    }

    private int displayMenu() {
        String[] options = {"Send Messages", "Show recently sent messages", "Quit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Please choose one of the following options:",
            "QuickChat Menu",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    private void sendMessages() {
        String input = JOptionPane.showInputDialog("How many messages do you wish to enter?");
        try {
            int numMessages = Integer.parseInt(input);
            if (numMessages <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                return;
            }
            for (int i = 1; i <= numMessages; i++) {
                createMessage(i);
            }
            JOptionPane.showMessageDialog(null, 
                "Total messages processed: " + numMessages + "\n" +
                "Total messages sent: " + Message.returnTotalMessages());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }

    private void createMessage(int messageNum) {
        String recipient;
        while (true) {
            recipient = JOptionPane.showInputDialog("Message " + messageNum + "\nEnter recipient phone number (format: +27123456789):");
            if (recipient == null) return;
            if (validateRecipient(recipient)) {
                JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            }
        }

        String messageContent;
        while (true) {
            messageContent = JOptionPane.showInputDialog("Enter your message:");
            if (messageContent == null) return;
            if (messageContent.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Message content cannot be empty.");
                continue;
            }
            if (validateMessageLength(messageContent)) {
                JOptionPane.showMessageDialog(null, "Message ready to send.");
                break;
            } else {
                int excess = messageContent.length() - 250;
                JOptionPane.showMessageDialog(null, 
                    "Message exceeds 250 characters by " + excess + ", please reduce size.");
            }
        }

        Message message = new Message(recipient, messageContent);
        String result = message.sentMessage();
        JOptionPane.showMessageDialog(null, result);
    }

    private boolean validateRecipient(String recipient) {
        return recipient.length() <= 10 && recipient.startsWith("+") && 
               recipient.substring(1).matches("\\d+");
    }

    private boolean validateMessageLength(String message) {
        return message.length() <= 250;
    }

    private void showRecentMessages() {
        if (Message.returnTotalMessages() == 0) {
            JOptionPane.showMessageDialog(null, "Coming Soon.\n\nNo messages sent yet.");
        } else {
            String messages = Message.printMessages();
            JOptionPane.showMessageDialog(null, "Coming Soon.\n\n" + messages);
        }
    }
}
