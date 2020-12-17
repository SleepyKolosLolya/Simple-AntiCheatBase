package acname.ac.features.commands.actions;

import acname.ac.features.commands.AbstractCommand;
import acname.ac.Global;
import acname.ac.util.chat.ChatManager;
import acname.ac.util.data.Data;
import acname.ac.util.data.PluginUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Alerts extends AbstractCommand {

    public Alerts() {
        super("alerts");
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Data data = PluginUtils.getDataByUUID(((Player) sender).getUniqueId());
            data.alerts = !data.alerts;
            sender.sendMessage(
                    ChatManager.colorCodes(
                            ChatManager.prefixReplace(
                                    ChatManager.LanguageAccess.getString(
                                            (data.alerts ? "onAlertsEnabled" : "onAlertsDisabled")
                                    )
                            )
                    )
            );
        } else {
            Global.LOGGER.info(ChatManager.LanguageAccess.prefix() + "You can't execute this command in console");
        }
        return true;
    }
}
