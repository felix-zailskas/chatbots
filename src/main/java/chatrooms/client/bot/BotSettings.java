package chatrooms.client.bot;

/**
 * Bot settings
 */
public class BotSettings {

    public static final int MAX_BOTS = 30;
    public static final int MAX_BOT_THREAD_POOL = MAX_BOTS * 2;
    public static final int MIN_SLEEP_TIME = 3000;
    public static final int MAX_SLEEP_TIME = 7000;
    public static final int COMPOUND_CHANCE = 20;
    public static final int SPAM_CHANCE = 85;
    public static final int MAX_SPAM_DURATION = 10;
    public static final int MIGRATION_CHANCE = 80;

}
