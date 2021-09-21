package chatrooms.client.bot;

import chatrooms.view.BotManagerPanel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Bot manager, keeps track of threads and each bot
 */
public class BotManager {

    private final ExecutorService executorService;
    private final ArrayList<Bot> bots;
    private final PropertyChangeSupport changeSupport;
    private final int maxBots;

    /**
     * Create a new Bot Manager and executor service
     */
    public BotManager(int maxBots) {
        this.maxBots = maxBots;
        int maxBotThreadPool = BotSettings.MAX_BOT_THREAD_POOL;
        executorService = Executors.newFixedThreadPool(maxBotThreadPool);
        this.bots = new ArrayList<>();
        changeSupport = new PropertyChangeSupport(this);
        setupGUI();
    }

    /**
     * Default constructors
     */
    public BotManager() {
        this(BotSettings.MAX_BOTS);
    }

    /**
     * Getter for the maximum number of bots this BotManager can contain
     *
     * @return maximum amount of bots
     */
    public int getMAX_BOTS() {
        return this.maxBots;
    }

    /**
     * Getter for the current amount of bots
     *
     * @return size of the Bot ArrayList
     */
    public int getAmountBots() {
        return bots.size();
    }

    /**
     * Sets up the basics for the GUI
     */
    private void setupGUI() {
        SwingUtilities.invokeLater(() -> {
            BotManagerPanel panel = new BotManagerPanel(this);
            JFrame frame = new JFrame();

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    /**
     * Adds a bot to the executor service
     *
     * @param bot Newly created bot
     */
    public void addBot(Bot bot) {
        executorService.submit(bot);
        bots.add(bot);
        PropertyChangeEvent botAddedEvent = new PropertyChangeEvent(this, "New Bot", null, bot);
        changeSupport.firePropertyChange(botAddedEvent);
    }

    /**
     * Create an ArrayList of this.bos
     *
     * @return Newly created arraylist of current bots
     */
    public ArrayList<Bot> getBots() {
        return new ArrayList<>(this.bots);
    }

    /**
     * Remove all bots from the Arraylist
     * NEEDS TO BE UPDATED TO INCORPORATE EXECUTOR SERVICE TERMINATION!
     */
    public void killAllBots() {
        bots.forEach(Bot::shutdown);
        bots.clear();
        PropertyChangeEvent killBotsEvent = new PropertyChangeEvent(this, "Kill Bots", bots, null);
        changeSupport.firePropertyChange(killBotsEvent);
    }

    /**
     * Method used to add PropertyChangeListeners to changeSupport
     *
     * @param listener listener to be added to changeSupport
     */
    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Fire a PropertyChangedEvent in oreder to update the text displayed in the BotManagerPanel
     */
    public void updateEvent() {
        PropertyChangeEvent updateEvent = new PropertyChangeEvent(this, "Update Bots", null, null);
        changeSupport.firePropertyChange(updateEvent);
    }
}
