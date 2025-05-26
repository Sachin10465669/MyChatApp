/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mychatapp;

/**
 *
 * @author Sachin
 */
public class MessageTest {

    public static void main(String[] args) {
        runAllTests();
    }

    public static void runAllTests() {
        System.out.println("Starting Unit Tests...\n");

        testMessageLengthValidation();
        testRecipientValidation();
        testMessageHashCreation();
        testMessageIDGeneration();

        System.out.println("\nAll tests completed!");
    }

    public static void testMessageLengthValidation() {
        System.out.println("=== Testing Message Length Validation ===");

        String validMessage = "Hi Mike, let's meet at 3pm.";
        Message m1 = new Message("+27821234567", validMessage);
        assert validMessage.length() <= 250 : "Message should be valid.";
        System.out.println("Test Case 1 passed.");

        String longMessage = "A".repeat(260);
        assert longMessage.length() > 250 : "Message should exceed limit.";
        System.out.println("Test Case 2 passed.");
    }

    public static void testRecipientValidation() {
        System.out.println("\n=== Testing Recipient Validation ===");

        Message m2 = new Message("+27821234567", "Hello");
        int result = m2.checkRecipientCell();
        assert result > 0 : "Valid number should pass.";
        System.out.println("Test Case 1 passed.");

        Message m3 = new Message("2782123456", "Hello");
        assert m3.checkRecipientCell() == -1 : "Missing '+' should fail.";
        System.out.println("Test Case 2 passed.");

        Message m4 = new Message("+ABC1234567", "Hello");
        assert m4.checkRecipientCell() == -1 : "Invalid digits should fail.";
        System.out.println("Test Case 3 passed.");
    }

    public static void testMessageHashCreation() {
        System.out.println("\n=== Testing Message Hash Creation ===");

        Message m5 = new Message("+27821234567", "Hello world");
        String hash = m5.createMessageHash();
        assert hash.contains("HELLOWORLD") : "Hash should contain uppercased first and last word.";
        System.out.println("Hash: " + hash);
        System.out.println("Test passed.");
    }

    public static void testMessageIDGeneration() {
        System.out.println("\n=== Testing Message ID Generation ===");

        Message m6 = new Message("+27821234567", "Testing ID");
        String id = m6.getMessageID();
        assert id.length() == 10 : "ID should be 10 digits.";
        assert id.matches("\\d{10}") : "ID should only contain digits.";
        System.out.println("Generated ID: " + id);
        System.out.println("Test passed.");
    }
}
