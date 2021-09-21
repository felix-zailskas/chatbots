package chatrooms.client.bot;

import chatrooms.message.Messages;

/**
 * Migratory Bot, a sub type of Bot but contains a random chance of leaving concurrent chatroom
 */
public class MigratoryBot extends Bot {

    private boolean justLeft;
    private final int migrationChance;

    /**
     * Creates new Migratory Bot
     */
    public MigratoryBot(int migrationChance) {
        super();
        this.migrationChance = migrationChance;
        this.justLeft = true;
    }

    /**
     * Creates new Migratory Bot with default migration chance
     */
    public MigratoryBot() {
        this(BotSettings.MIGRATION_CHANCE);
    }

    /**
     * Boolean function to determine if the bot will indeed leave concurrent chatroom
     *
     * @return if the bot will leave this chatroom
     */
    public boolean leaving() {
        if (this.justLeft) {
            this.justLeft = false;
            return false;
        }
        int chance = random.nextInt(100);
        return chance > migrationChance && !justLeft;
    }

    /**
     * Has the bot jus left a server?
     *
     * @return justLeft
     */
    public boolean isJustLeft() {
        return this.justLeft;
    }

    /**
     * Setter for justLeft boolean
     *
     * @param justLeft bolean
     */
    public void setJustLeft(boolean justLeft) {
        this.justLeft = justLeft;
    }

    /**
     * Because Bot implements Runnable, the sub classes of Bot should as well
     * Connects to chat room, and because it is a migratory bot, it may leave this chatroom
     */
    @Override
    public void run() {
        getPortFromServer();
        if (connectToChatRoom()) {
            return;
        }
        running = true;
        while (running) {
            if (leaving()) {
                this.writeMessage(Messages.LEAVING_MESSAGE);
                justLeft = true;
                getPortFromServer();
                if (connectToChatRoom()) {
                    return;
                }
            }
            int sleepingTime = BotSettings.MIN_SLEEP_TIME + random.nextInt(BotSettings.MAX_SLEEP_TIME);
            try {
                Thread.sleep(sleepingTime);
                if (!running) break;
                generateMessage();
                readFromChatRoom();
            } catch (InterruptedException e) {
                System.out.println("Unable to let thread sleep!");
            }
        }
    }
}
