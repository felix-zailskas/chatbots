package chatrooms.client.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Migratory bot test
 */
class MigratoryBotTest {

    private MigratoryBot migratoryBot;

    /**
     * Create a new migratory bot before each j unit test
     */
    @BeforeEach
    void setUp() {
        this.migratoryBot = new MigratoryBot(0);
    }

    /**
     * Test the leaving function of a migratory bot set with migration chance of 0, so leaving should always
     * return TRUE
     */
    @Test
    void leaving() {
        assertTrue(migratoryBot.isJustLeft());
        assertFalse(migratoryBot.leaving());
        migratoryBot.setJustLeft(false);
        assertTrue(migratoryBot.leaving());
    }
}