package acname.ac.util.chat;

import acname.ac.features.checks.Check;
import acname.ac.Global;
import acname.ac.util.data.Data;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public final class ChatManager {

    private ChatManager() {
    }

    public static TextComponent generateVerboseLog(Data data, Check check, String verbose, float multiplier) {
        String style = Global.getLanguage().getString("style") + ".";
        TextComponent mainMessage = new TextComponent(placeholder(Global.getLanguage().getString(style + "alerts"), data, check));
        // TODO: Complete this code
        return mainMessage;
    }

    public static String unknownCommand(String command) {
        String message = colorCodes(ChatManager.LanguageAccess.getString("unknownCommand"));
        message = message.replace("%prefix%", ChatManager.LanguageAccess.prefix());
        message = message.replace("%unknown_command%", command);
        return message;
    }

    public static String placeholder(String str, Data data, @NotNull Check check) {
        str = colorCodes(str);
        str = prefixReplace(str);
        str = str.replace("%player%", Objects.requireNonNull(data.getPlayer().getName()));
        str = str.replace("%check%", check.getVerboseName());
        str = str.replace("%current_vl%", data.checkFinder(check.getClass()).getVL().getValue() + "");
        return str;
    }

    public static String colorCodes(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String prefixReplace(String txt) {
        return txt.replace("%prefix%", LanguageAccess.prefix());
    }

    public static class LanguageAccess {

        public static String prefix() {
            return colorCodes(getString("prefix"));
        }

        public static Object get(String str) {
            return colorCodes(Global.getLanguage().getString(Global.getLanguage().getString("style") + "." + str));
        }

        public static String getString(String str) {
            return colorCodes(Global.getLanguage().getString(Global.getLanguage().getString("style") + "." + str));
        }

    }

}
