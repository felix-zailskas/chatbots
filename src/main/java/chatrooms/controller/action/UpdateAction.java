package chatrooms.controller.action;

import chatrooms.client.bot.BotManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to update the display of a BotManager
 */
public class UpdateAction extends AbstractAction {

    private final BotManager botManager;

    /**
     * Create a new UpdateAction
     */
    public UpdateAction(BotManager botManager) {
        super("Update");
        this.botManager = botManager;
    }

    /**
     * Update the display of a BotManager
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        botManager.updateEvent();
    }
}
