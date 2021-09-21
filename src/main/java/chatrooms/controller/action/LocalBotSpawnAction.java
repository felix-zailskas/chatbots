package chatrooms.controller.action;

import chatrooms.client.bot.BotManager;
import chatrooms.client.bot.BotNames;
import chatrooms.client.bot.LocalBot;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to spawn a local bot.
 */
public class LocalBotSpawnAction extends AbstractAction {

    private final BotManager botManager;

    /**
     * Create new LocalBotSpawnAction
     */
    public LocalBotSpawnAction(BotManager botManager) {
        super("Spawn Local Bot");
        this.botManager = botManager;
    }

    /**
     * Spawn a local bot in the BotManager if it can contain any more bots and there are available names.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (botManager.getAmountBots() < botManager.getMAX_BOTS() && BotNames.BOT_NAMES.size() > 0) {
            botManager.addBot(new LocalBot());
        }
    }
}
