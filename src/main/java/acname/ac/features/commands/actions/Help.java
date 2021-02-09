package acname.ac.features.commands.actions;

import acname.ac.features.commands.AbstractCommand;
import acname.ac.util.chat.ChatHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Help extends AbstractCommand {

    // First argument must be "help"
    public Help() {
        super("help");
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args) {

        // Permission is already checked in ACCommandExecutor.java
        try {
            sender.sendMessage(ChatHelper.prefixReplace(ChatHelper.getString("help")));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // It returns value that means is command executed successfully.
        return false;
    }

}
