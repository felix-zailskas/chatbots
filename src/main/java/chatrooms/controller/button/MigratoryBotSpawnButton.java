package chatrooms.controller.button;

import chatrooms.client.bot.BotManager;
import chatrooms.controller.action.MigratoryBotSpawnAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Button to spawn a migratory bot.
 */
public class MigratoryBotSpawnButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setMnemonic(KeyEvent.VK_S);
    }

    /**
     * Create a Button to spawn a migratory bot
     *
     * @param botManager BotManager to spawn in
     */
    public MigratoryBotSpawnButton(BotManager botManager) {
        super(new MigratoryBotSpawnAction(botManager));
        setButtonProperties();
    }
}
