package acname.ac.features.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand {

    private final String firstArg;

    public AbstractCommand(String arg) {
        this.firstArg = arg.toLowerCase();
    }

    public String getFirstArg() {
        return firstArg;
    }

    public abstract boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args);

}
