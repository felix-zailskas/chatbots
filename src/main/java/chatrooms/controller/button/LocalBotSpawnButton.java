package chatrooms.controller.button;

import chatrooms.client.bot.BotManager;
import chatrooms.controller.action.LocalBotSpawnAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Button to spawn a local bot.
 */
public class LocalBotSpawnButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setMnemonic(KeyEvent.VK_S);
    }

    /**
     * Create a Button to spawn a local bot
     *
     * @param botManager BotManager to spawn in
     */
    public LocalBotSpawnButton(BotManager botManager) {
        super(new LocalBotSpawnAction(botManager));
        setButtonProperties();
    }

}
