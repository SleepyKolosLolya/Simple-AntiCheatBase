package acname.ac.features.commands;

import acname.ac.features.commands.actions.Alerts;
import acname.ac.features.commands.actions.Debug;
import acname.ac.features.commands.actions.Help;
import acname.ac.features.commands.actions.ThemeChanger;
import acname.ac.plugin.Global;
import acname.ac.util.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ACCommandExecutor extends Global implements CommandExecutor {

    AbstractCommand[] commands = {
            new ThemeChanger(),
            new Alerts(),
            new Debug(),
            new Help(),
    };

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        if (commandSender.hasPermission("acb.cmd")) {
            if (strings.length != 0) {
                for (AbstractCommand acCommand : commands) {
                    if (acCommand.getFirstArg().equals(strings[0])) {
                        return acCommand.onCommand(commandSender, command, label, strings);
                    }
                }
                commandSender.sendMessage(ChatManager.unknownCommand(strings[0]));
            } else {
                commandSender.sendMessage(ChatManager.unknownCommand("none"));
            }
            return true;
        }
        return true;
    }

}
