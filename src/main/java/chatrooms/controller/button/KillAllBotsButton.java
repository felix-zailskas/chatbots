package chatrooms.controller.button;

import chatrooms.client.bot.BotManager;
import chatrooms.controller.action.KillAllBotsAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Button to kill all bots of a BotManager
 */
public class KillAllBotsButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setMnemonic(KeyEvent.VK_S);
    }

    /**
     * Create Button to kill all bots of a BotManager
     *
     * @param botManager BotManager to kill in
     */
    public KillAllBotsButton(BotManager botManager) {
        super(new KillAllBotsAction(botManager));
        setButtonProperties();
    }
}
