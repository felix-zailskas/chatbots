package chatrooms.controller.button;

import chatrooms.client.bot.BotManager;
import chatrooms.controller.action.UpdateAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * A button to update the display of a BotManager
 */
public class UpdateButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setMnemonic(KeyEvent.VK_S);
    }

    /**
     * Create an update Button
     *
     * @param botManager BotManager to update
     */
    public UpdateButton(BotManager botManager) {
        super(new UpdateAction(botManager));
        setButtonProperties();
    }
}
