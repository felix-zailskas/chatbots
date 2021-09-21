package chatrooms.client.bot;

/**
 * Creates a Local chat Bot
 */
public class LocalBot extends Bot {

    /**
     * Uses super constructor
     */
    public LocalBot() {
        super();
    }

    /**
     * Because Bot implements Runnable, the sub classes of Bot should as well
     * Connects to chat room, and because it is a local bot, it is locked to this room
     */
    @Override
    public void run() {
        getPortFromServer();
        if (connectToChatRoom()) {
            return;
        }
        running = true;
        while (running) {
            int sleepingTime = BotSettings.MIN_SLEEP_TIME + random.nextInt(BotSettings.MAX_SLEEP_TIME);
            try {
                Thread.sleep(sleepingTime);
                if (!running) break;
                generateMessage();
                readFromChatRoom();
            } catch (InterruptedException e) {
                System.out.println("Unable to thread sleep!");
            }

        }
    }
}
