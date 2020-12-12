package acname.ac.features.commands.actions;

import acname.ac.features.commands.AbstractCommand;
import acname.ac.plugin.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ThemeChanger extends AbstractCommand {

    public ThemeChanger() {
        super("theme");
    }


    // TODO: 12.12.2020 Complete theme changer command
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (args.length == 2) {
            if (Global.getLanguage().getStringList("").contains(args[1])) {
                Global.getLanguage().set("style", args[1]);
            } else {

            }
        }
        return true;
    }
}
