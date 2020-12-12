package acname.ac.features.commands.actions;

import acname.ac.features.commands.AbstractCommand;
import acname.ac.features.modules.util.Check;
import acname.ac.plugin.Global;
import acname.ac.util.ChatManager;
import acname.ac.util.Data;
import acname.ac.util.PluginUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debug extends AbstractCommand {

    public Debug() {
        super("debug");
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 2) {
                final Data data = PluginUtils.getDataByUUID(((Player) sender).getUniqueId());

                String checkName = args[1].toLowerCase();
                for (Check check : data.checks) {
                    if (check.getConfigName().equalsIgnoreCase(checkName)) {
                        check.debug = !check.debug;
                        sender.sendMessage(
                                ChatManager.colorCodes(
                                        ChatManager.LanguageAccess.getString(check.debug ? "onDebugEnabled" : "onDebugDisabled")
                                                .replace("%prefix%", ChatManager.LanguageAccess.prefix())
                                )
                        );
                        return true;
                    }
                }
                sender.sendMessage(
                        ChatManager.colorCodes(
                                ChatManager.LanguageAccess.getString("unknownCheck")
                                        .replace("%prefix%", ChatManager.LanguageAccess.prefix())
                                        .replace("%arg%", checkName)
                        )
                );
            } else {
                sender.sendMessage(
                        ChatManager.colorCodes(
                                ChatManager.LanguageAccess.getString("wrongNumberOfArguments")
                                        .replace("%prefix%", ChatManager.LanguageAccess.prefix())
                                        .replace("%exp%", 2 + "")
                                        .replace("%args_length%", args.length + "")
                        )
                );
            }
        } else {
            Global.logger.info(ChatManager.LanguageAccess.prefix() + "You can't execute this command in console");
            return true;
        }
        return true;
    }

}
