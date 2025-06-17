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
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;
    private static int messageCounter = 1;
    private static ArrayList<Message> allMessages = new ArrayList<>();

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
}
