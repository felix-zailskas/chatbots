package chatrooms.client.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Bot manager test
 */
class BotManagerTest {

    private BotManager botManager;

    /**
     * Before each test, create new bot manager
     */
    @BeforeEach
    void setUp() {
        botManager = new BotManager();
    }

    /**
     * Test max amount of bots and the getter
     */
    @Test
    void getMAX_BOTS() {
        assertEquals(BotSettings.MAX_BOTS, botManager.getMAX_BOTS());
    }

    /**
     * Test the amount of bots using the getter method for current amount of bots
     */
    @Test
    void getAmountBots() {
        botManager.addBot(new LocalBot());
        assertEquals(1, botManager.getAmountBots());
    }

    /**
     * Test the adding bot method to see if adding a bot will equal the original java object
     */
    @Test
    void addBot() {
        LocalBot localBot = new LocalBot();
        String name = localBot.getName();
        botManager.addBot(localBot);
        assertEquals(localBot, botManager.getBots().get(0));
        assertEquals(name, botManager.getBots().get(0).getName());
    }

    /**
     * Testing if creating an array of bots will equal the array inside the bot manager filled with those bots
     */
    @Test
    void getBots() {
        LocalBot localBot = new LocalBot();
        MigratoryBot migratoryBot = new MigratoryBot();
        ArrayList<Bot> bots = new ArrayList<>(Arrays.asList(localBot, migratoryBot));
        botManager.addBot(localBot);
        botManager.addBot(migratoryBot);
        assertEquals(bots, botManager.getBots());
    }

}