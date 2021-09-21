package chatrooms.view;

import chatrooms.client.bot.Bot;
import chatrooms.client.bot.BotManager;
import chatrooms.controller.button.KillAllBotsButton;
import chatrooms.controller.button.LocalBotSpawnButton;
import chatrooms.controller.button.MigratoryBotSpawnButton;
import chatrooms.controller.button.UpdateButton;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel that contains the information on the current bots.
 * Actions like spawning and killing bots are executed through this GUI element.
 */
public class BotManagerPanel extends JPanel implements PropertyChangeListener {

    private final JTextArea textArea;

    private final BotManager botManager;

    /**
     * Creates a new BotManagerPanel.
     * Contains buttons for spawning and killing bots.
     * Buttons are contained in their own panel, the TextArea is contained in a separate Panel.
     *
     * @param botManager the BotManager controlled by this panel
     */
    public BotManagerPanel(BotManager botManager) {
        this.botManager = botManager;
        botManager.addListener(this);

        int COLUMNS = 40;
        int ROWS = 32;
        textArea = new JTextArea(ROWS, COLUMNS);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        textArea.append("Current Bots: " + botManager.getAmountBots() + "/" + botManager.getMAX_BOTS() + "\n");

        JButton localSpawnButton = new LocalBotSpawnButton(botManager);
        JButton migratorySpawnButton = new MigratoryBotSpawnButton(botManager);
        JButton killButton = new KillAllBotsButton(botManager);
        JButton updateButton = new UpdateButton(botManager);

        int GAP = 3;
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(localSpawnButton);
        buttonPanel.add(migratorySpawnButton);
        buttonPanel.add(killButton);
        buttonPanel.add(updateButton);

        add(putInTitledScrollPane(textArea));
        add(buttonPanel);

    }

    /**
     * Creates a new JPanel of a singular component and sets its title and border using a BorderFactory
     *
     * @param component The component to be put in the JPanel
     * @return a JPanel containing a title and border
     */
    private JPanel putInTitledScrollPane(JComponent component) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder("Botmanager Feed"));
        wrapperPanel.add(new JScrollPane(component));
        return wrapperPanel;
    }

    /**
     * PropertyChange method to listen to PropertyChangedEvents from the BotManager.
     * Changes the text of the TextArea to the current status of the BotManager.
     *
     * @param evt PropertyChangedEvent of the BotManager
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        StringBuilder newText = new StringBuilder();
        newText.append("Current Bots: ").append(botManager.getAmountBots()).append("/").append(botManager.getMAX_BOTS()).append("\n");
        for (Bot b : botManager.getBots()) {
            newText.append(b.getName()).append(" is currently on port: ").append(b.getPortNumber()).append("\n");
        }
        textArea.setText(newText.toString());
    }
}
