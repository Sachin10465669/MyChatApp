/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mychatapp;

/**
 *
 * @author Sachin
 */
// Message.java
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
// Inside Message class, add this static method
public static void addMessageForTest(Message msg) {
    allMessages.add(msg);
}

    // Populate test messages
public static void populateTestData() {
    allMessages.add(new Message("+27111111111", "Did you get the cake?"));
    allMessages.add(new Message("+27222222222", "Where are you? You are late! I have asked you to be on time."));
    allMessages.add(new Message("+27333333333", "Yohoooo, I am at your gate."));
    allMessages.add(new Message("+27444444444", "It is dinner time !"));
    allMessages.add(new Message("+27555555555", "Ok, I am leaving without you."));
}
    private final String messageID;
    private final int numMessagesSent;
    private final String recipient;
    private final String message;
    private final String messageHash;
    private static int messageCounter = 1;
    private static final ArrayList<Message> allMessages = new ArrayList<>();

    // Constructor
    public Message(String recipient, String message) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.numMessagesSent = messageCounter++;
        this.messageHash = createMessageHash();
    }

    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Boolean: checkMessageID()
    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    // Int: checkRecipientCell()
    public int checkRecipientCell() {
        if (this.recipient.length() <= 10 && this.recipient.startsWith("+")) {
            String number = this.recipient.substring(1);
            try {
                Long.parseLong(number);
                return this.recipient.length();
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    // String: createMessageHash()
    public String createMessageHash() {
        String[] words = this.message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
        String firstTwoDigits = this.messageID.length() >= 2 ? this.messageID.substring(0, 2) : this.messageID;
        return firstTwoDigits + ":" + this.numMessagesSent + ":" + firstWord + lastWord;
    }

    // String: sentMessage()
    public String sentMessage() {
        String messageDetails = String.format(
            "MessageID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s",
            this.messageID, this.messageHash, this.recipient, this.message
        );

        JOptionPane.showMessageDialog(null, messageDetails, "Message Details", JOptionPane.INFORMATION_MESSAGE);

        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null,
            "What would you like to do with this message?",
            "Message Options",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        switch (choice) {
            case 0: allMessages.add(this); return "Message successfully sent.";
            case 1: return "Press 0 to delete message.";
            case 2: storeMessage(); return "Message successfully stored.";
            default: return "No action taken.";
        }
    }

    // String: printMessages()
    public static String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== All Sent Messages ===\n");
        for (Message msg : allMessages) {
            sb.append(String.format("MessageID: %s, Message Hash: %s, Recipient: %s, Message: %s\n",
                msg.messageID, msg.messageHash, msg.recipient, msg.message));
        }
        return sb.toString();
    }

    // Int: returnTotalMessages()
    public static int returnTotalMessages() {
        return allMessages.size();
    }

    // Store message in JSON
    public void storeMessage() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MessageData data = new MessageData(this.messageID, this.messageHash, 
                this.recipient, this.message, this.numMessagesSent);

            FileWriter writer = new FileWriter("message_" + this.messageID + ".json");
            gson.toJson(data, writer);
            writer.close();

            JOptionPane.showMessageDialog(null, "Message stored in JSON file successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
    }

    // Helper class
    private class MessageData {
        String messageID, messageHash, recipient, message;
        int numMessagesSent;

        MessageData(String id, String hash, String rec, String msg, int num) {
            this.messageID = id;
            this.messageHash = hash;
            this.recipient = rec;
            this.message = msg;
            this.numMessagesSent = num;
        }
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public int getNumMessagesSent() { return numMessagesSent; }
    // Display Sender and Recipient Info
    public static String displaySenderRecipient() {
        if (allMessages.isEmpty()) return "No messages to display.";
        StringBuilder sb = new StringBuilder("=== Sender & Recipient Info ===\n");
        for (Message msg : allMessages) {
            sb.append(String.format("Sender: SystemUser | Recipient: %s\n", msg.recipient));
        }
        return sb.toString();
    }

    // Display Longest Message
    public static String displayLongestMessage() {
        if (allMessages.isEmpty()) return "No messages to check.";
        Message longest = allMessages.get(0);
        for (Message msg : allMessages) {
            if (msg.message.length() > longest.message.length()) {
                longest = msg;
            }
        }
        return String.format("Longest Message:\nMessage ID: %s\nLength: %d\nMessage: %s",
            longest.messageID, longest.message.length(), longest.message);
    }

    // Search by Message ID
    public static String searchByMessageID(String id) {
        for (Message msg : allMessages) {
            if (msg.messageID.equals(id)) {
                return String.format("Found Message:\nMessage ID: %s\nRecipient: %s\nMessage: %s\nHash: %s",
                    msg.messageID, msg.recipient, msg.message, msg.messageHash);
            }
        }
        return "No message found with ID: " + id;
    }

    // Search by Recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages for recipient: " + recipient + "\n");
        boolean found = false;
        for (Message msg : allMessages) {
            if (msg.recipient.equals(recipient)) {
                found = true;
                sb.append(String.format("- ID: %s | Message: %s | Hash: %s\n", msg.messageID, msg.message, msg.messageHash));
            }
        }
        return found ? sb.toString() : "No messages found for recipient: " + recipient;
    }

    // Delete by Message Hash
    public static String deleteByMessageHash(String hash) {
        for (Message msg : allMessages) {
            if (msg.messageHash.equals(hash)) {
                allMessages.remove(msg);
                return "Message with hash " + hash + " deleted successfully.";
            }
        }
        return "No message found with the given hash: " + hash;
    }

    // Display Full Report
    public static String displayFullReport() {
        if (allMessages.isEmpty()) return "No messages to report.";
        StringBuilder sb = new StringBuilder("=== Full Message Report ===\n");
        sb.append("Total messages sent: ").append(allMessages.size()).append("\n\n");
        for (Message msg : allMessages) {
            sb.append(String.format("ID: %s\nRecipient: %s\nMessage: %s\nHash: %s\n\n",
                msg.messageID, msg.recipient, msg.message, msg.messageHash));
        }
        return sb.toString();
    }

    // Run Unit Tests
    public static String runUnitTests() {
        StringBuilder sb = new StringBuilder("=== Unit Test Results ===\n");

        // Sample test message
        Message test = new Message("+27123456789", "Hello there! Testing unit methods.");

        // Test 1: checkMessageID
        sb.append("Test 1 - checkMessageID: ").append(test.checkMessageID() ? "PASS" : "FAIL").append("\n");

        // Test 2: checkRecipientCell
        sb.append("Test 2 - checkRecipientCell: ").append(test.checkRecipientCell() == 12 ? "PASS" : "FAIL").append("\n");

        // Test 3: createMessageHash format
        String hash = test.createMessageHash();
        sb.append("Test 3 - createMessageHash (has colon & words): ").append(hash.contains(":") ? "PASS" : "FAIL").append("\n");

        // Test 4: add and delete
        allMessages.add(test);
        String delResult = deleteByMessageHash(test.messageHash);
        sb.append("Test 4 - Delete Message by Hash: ").append(delResult.contains("deleted") ? "PASS" : "FAIL").append("\n");

        return sb.toString();
    }
}
