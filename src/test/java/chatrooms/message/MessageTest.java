package chatrooms.message;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Message Test base messages arraylist
 */
public class MessageTest {

    private final ArrayList<String> baseMessages = new ArrayList<>(Arrays.asList(
            "Hi!", "How are you?", "Do you have a lighter?", "Can anyone point me to the washroom?", "Are you really " +
                    "not a bot?", "Has anyone ever told you that you type really slow?", "I promise I am not a bot",
            "I could really fancy a beer", "I just like typing messages", "I might leave the server, MIGHT.",
            "A lot of bots just type the same thing as me.", "How many times will I be instantiated?",
            "Are you kidding me!", "Do I ask a question?", "Random random random", "Compound messages make " +
                    "no sense."));

    /**
     * Testing if static arraylist class constant is equal to newly created arraylist
     */
    @Test
    public void testStaticMessages() {
        assertEquals(baseMessages, Messages.BASE_MESSAGES);
    }

    /**
     * Test the leaving message, when a bot leaves a chatroom
     */
    @Test
    public void testLeavingMessage() {
        String leavingMessage = "I am leaving!";
        assertEquals(leavingMessage, Messages.LEAVING_MESSAGE);
    }
}
