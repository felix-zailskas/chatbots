package chatrooms.client.bot;

import chatrooms.message.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Bot test J unit class
 */
public class BotTest {

    private Bot localBot;

    /**
     * Set up a new local bot before each test
     */
    @BeforeEach
    void setUp() {
        localBot = new LocalBot();
    }

    /**
     * Set port number for a port, test getter method to retreive port
     */
    @Test
    void getPortNumber() {
        localBot.setPortNumber(123456);
        assertEquals(123456, localBot.getPortNumber());
    }

    /**
     * Test the getter name function to return the name of the bot, names are chosen randomly so we cannot predetermine
     * the name of the local bot
     */
    @Test
    void getName() {
        String name = localBot.getName();
        assertEquals(name, localBot.getName());
        assertFalse(BotNames.BOT_NAMES.contains(localBot.getName()));
    }

    /**
     * Retrieve the base message feed arraylist of the local bot
     */
    @Test
    void getMessages() {
        assertEquals(Messages.BASE_MESSAGES, localBot.getMessages());
    }

}
