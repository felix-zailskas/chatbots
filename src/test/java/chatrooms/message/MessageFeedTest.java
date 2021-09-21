package chatrooms.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Message Feed test
 */
public class MessageFeedTest {

    private MessageFeed messageFeed;
    private static final String TEST_MESSAGE = "Test Message";

    /**
     * Create a new MessageFeed object with default empty arraylist
     */
    @BeforeEach
    public void setup() {
        messageFeed = new MessageFeed();
    }

    /**
     * Test if message feed object is not null
     */
    @Test
    public void newMessageFeedTest() {
        assertNotNull(messageFeed);
    }

    /**
     * Test if message feed arraylist is indeed empty and size 0
     */
    @Test
    public void emptyMessageFeedTest() {
        assertEquals(0, messageFeed.getMessages().size());
    }

    /**
     * Fill message feed with base message arraylist, compare the two if they are equal
     */
    @Test
    public void filledMessageFeedTest() {
        assertEquals(Messages.BASE_MESSAGES, new MessageFeed(Messages.BASE_MESSAGES).getMessages());
    }

    /**
     * Set the latest message in the messaege feed and use getters to compare the returned value
     */
    @Test
    public void setMessageTest() {
        assertEquals(0, messageFeed.getMessages().size());
        messageFeed.setMessage(TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, messageFeed.getMessage());
        assertEquals(TEST_MESSAGE, messageFeed.getMessages().get(0));
    }
}
