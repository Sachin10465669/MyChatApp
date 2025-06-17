package mychatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class MessageTest {

    @Test
    public void testMessageLengthValidation() {
        String validMessage = "Hi Mike, let's meet at 3pm.";
        Message m1 = new Message("+27821234567", validMessage);
        assertTrue(validMessage.length() <= 250, "Message should be valid.");

        String longMessage = "A".repeat(260);
        assertTrue(longMessage.length() > 250, "Message should exceed limit.");
    }

    @Test
    public void testRecipientValidation() {
        Message m2 = new Message("+27821234567", "Hello");
        int result = m2.checkRecipientCell();
        assertTrue(result > 0, "Valid number should pass.");

        Message m3 = new Message("2782123456", "Hello");
        assertEquals(-1, m3.checkRecipientCell(), "Missing '+' should fail.");

        Message m4 = new Message("+ABC1234567", "Hello");
        assertEquals(-1, m4.checkRecipientCell(), "Invalid digits should fail.");
    }

    @Test
    public void testMessageHashCreation() {
        Message m5 = new Message("+27821234567", "Hello world");
        String hash = m5.createMessageHash();
        assertTrue(hash.contains("HELLO") && hash.contains("WORLD"),
            "Hash should contain uppercased first and last word.");
    }

    @Test
    public void testMessageIDGeneration() {
        Message m6 = new Message("+27821234567", "Testing ID");
        String id = m6.getMessageID();
        assertEquals(10, id.length(), "ID should be 10 digits.");
        assertTrue(id.matches("\\d{10}"), "ID should only contain digits.");
    }

    @Test
    public void testCheckMessageID() {
        Message m = new Message("+27821234567", "Sample message");
        assertTrue(m.checkMessageID(), "Message ID length should be 10 or less.");
    }

    @Test
    public void testReturnTotalMessages() {
        int initialCount = Message.returnTotalMessages();

        Message m1 = new Message("+27821234567", "Hello 1");
        Message m2 = new Message("+27821234568", "Hello 2");

        Message.addMessageForTest(m1);
        Message.addMessageForTest(m2);

        int newCount = Message.returnTotalMessages();
        assertEquals(initialCount + 2, newCount);
    }

    @Test
    public void testPrintMessages() {
        Message m = new Message("+27821234567", "Test print message");
        Message.addMessageForTest(m);

        String output = Message.printMessages();

        assertTrue(output.contains(m.getMessageID()));
        assertTrue(output.contains(m.getRecipient()));
        assertTrue(output.contains(m.getMessage()));
    }

    @Test
    public void testStoreMessage() {
        Message m = new Message("+27821234567", "Testing store message");
        m.storeMessage();

        File file = new File("message_" + m.getMessageID() + ".json");
        assertTrue(file.exists(), "JSON file should be created.");

        file.delete();  // Clean up test file
    }
}
