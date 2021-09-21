package chatrooms.controller.action;

import chatrooms.client.bot.BotManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to kill all bots of a BotManager
 */
public class KillAllBotsAction extends AbstractAction {

    private final BotManager botManager;

    /**
     * Create a new KillAllBotsAction
     */
    public KillAllBotsAction(BotManager botManager) {
        super("Kill all Bots");
        this.botManager = botManager;
    }

    /**
     * Call the BotManagers method to kill all its bots.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        botManager.killAllBots();
    }
}
