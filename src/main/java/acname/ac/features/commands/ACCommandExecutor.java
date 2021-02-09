package acname.ac.features.commands;

import acname.ac.features.commands.actions.Alerts;
import acname.ac.features.commands.actions.Debug;
import acname.ac.features.commands.actions.Help;
import acname.ac.features.commands.actions.ThemeChanger;
import acname.ac.Global;
import acname.ac.util.chat.ChatHelper;
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
                commandSender.sendMessage(ChatHelper.unknownCommand(strings[0]));
            } else {
                commandSender.sendMessage(ChatHelper.unknownCommand("none"));
            }
            return true;
        }
        return true;
    }

}
