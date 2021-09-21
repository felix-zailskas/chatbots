package chatrooms.controller.action;

import chatrooms.client.bot.BotManager;
import chatrooms.client.bot.BotNames;
import chatrooms.client.bot.MigratoryBot;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to spawn a migratory bot.
 */
public class MigratoryBotSpawnAction extends AbstractAction {

    private final BotManager botManager;

    /**
     * Create new MigratoryBotSpawnAction
     */
    public MigratoryBotSpawnAction(BotManager botManager) {
        super("Spawn Migratory Bot");
        this.botManager = botManager;
    }

    /**
     * Spawn a migratory bot in the BotManager if it can contain any more bots and there are available names.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (botManager.getAmountBots() < botManager.getMAX_BOTS() && BotNames.BOT_NAMES.size() > 0) {
            botManager.addBot(new MigratoryBot());
        }
    }
}
