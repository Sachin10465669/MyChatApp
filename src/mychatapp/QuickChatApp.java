package mychatapp;

import javax.swing.JOptionPane;

public class QuickChatApp {
    private boolean loggedIn = false;
    private String currentUser = "";

    public static void main(String[] args) {
        QuickChatApp app = new QuickChatApp();
        app.run();
    }

    // Method to run without login (called from Main after successful login)
    public void runWithoutLogin(String username) {
        this.loggedIn = true;
        this.currentUser = username;
        displayWelcomeMessage();
        runMainLoop();
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
        runMainLoop();
    }

    private void runMainLoop() {
        boolean running = true;
        while (running) {
            int choice = displayMenu();
            switch (choice) {
                case 1 -> sendMessages();
                case 2 -> showRecentMessages();
                case 3 -> displaySenderRecipient();
                case 4 -> displayLongestMessage();
                case 5 -> searchMessageByID();
                case 6 -> searchMessagesByRecipient();
                case 7 -> deleteMessageByHash();
                case 8 -> displayFullReport();
                case 9 -> runUnitTests();
                case 10 -> populateTestData();
                case 11 -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat!");
                }
            }
        }
    }

    private boolean login() {
        // Simple login for GUI-only mode
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        if (username != null && !username.trim().isEmpty() && 
            password != null && !password.trim().isEmpty()) {
            loggedIn = true;
            currentUser = username;
            JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username + "!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
            return false;
        }
    }

    private void displayWelcomeMessage() {
        String welcomeMsg = currentUser.isEmpty() ? 
            "Welcome to QuickChat - Part 3 Enhanced Version" : 
            "Welcome to QuickChat - Part 3 Enhanced Version\nLogged in as: " + currentUser;
        JOptionPane.showMessageDialog(null, welcomeMsg, 
            "QuickChat", JOptionPane.INFORMATION_MESSAGE);
    }

    private int displayMenu() {
    String[] options = {
        "1. Send Msg", 
        "2. Recent Msgs", 
        "3. Sender/Recip",
        "4. Longest Msg",
        "5. Search by ID",
        "6. Search by Recip", 
        "7. Delete by Hash",
        "8. Full Report",
        "9. Run Tests",
        "10. Load Data",
        "11. Quit"
    };
    int choice = JOptionPane.showOptionDialog(null,
        "Choose an option:",
        "QuickChat Menu",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
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
        return recipient.length() <= 12 && recipient.startsWith("+") && 
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

    
    private void displaySenderRecipient() {
        String result = Message.displaySenderRecipient();
        JOptionPane.showMessageDialog(null, result, "Sender & Recipient Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayLongestMessage() {
        String result = Message.displayLongestMessage();
        JOptionPane.showMessageDialog(null, result, "Longest Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchMessageByID() {
        String messageID = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (messageID != null && !messageID.trim().isEmpty()) {
            String result = Message.searchByMessageID(messageID.trim());
            JOptionPane.showMessageDialog(null, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchMessagesByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number to search:");
        if (recipient != null && !recipient.trim().isEmpty()) {
            String result = Message.searchByRecipient(recipient.trim());
            JOptionPane.showMessageDialog(null, result, "Messages for Recipient", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteMessageByHash() {
        String messageHash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (messageHash != null && !messageHash.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete the message with hash: " + messageHash + "?",
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = Message.deleteByMessageHash(messageHash.trim());
                JOptionPane.showMessageDialog(null, result, "Deletion Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void displayFullReport() {
        String report = Message.displayFullReport();
        JOptionPane.showMessageDialog(null, report, "Full Message Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void runUnitTests() {
        String testResults = Message.runUnitTests();
        JOptionPane.showMessageDialog(null, testResults, "Unit Test Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private void populateTestData() {
        Message.populateTestData();
        JOptionPane.showMessageDialog(null, 
            "Test data populated successfully!\n\n" +
            "- Message 1: 'Did you get the cake?' (Sent)\n" +
            "- Message 2: 'Where are you? You are late! I have asked you to be on time.' (Stored)\n" +
            "- Message 3: 'Yohoooo, I am at your gate.' (Disregarded)\n" +
            "- Message 4: 'It is dinner time !' (Sent)\n" +
            "- Message 5: 'Ok, I am leaving without you.' (Stored)\n\n" +
            "You can now test all the features with this data!",
            "Test Data Loaded", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}