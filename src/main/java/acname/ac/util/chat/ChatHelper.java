package acname.ac.util.chat;

import acname.ac.features.checks.Check;
import acname.ac.Global;
import acname.ac.util.config.ConfigCache;
import acname.ac.util.data.Data;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public final class ChatHelper {

    private ChatHelper() {
    }


    public static TextComponent generateVerboseLog(Data data, Check check, String verbose, float multiplier) {

        TextComponent mainMessage = new TextComponent(placeholder(
                (String) ConfigCache.Language.getThemeValue("alerts"), data, check, verbose, multiplier));

        mainMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(placeholder(
                (String) ConfigCache.Language.getThemeValue("alertinfo"), data, check, verbose, multiplier)).create()));

        return mainMessage;
    }

    public static String placeholder(String var1, Data data, Check check, String verbose, float multiplier) {

        var1 = prefixReplace(var1);
        var1 = var1.replace("%player%", Objects.requireNonNull(data.getPlayer().getName()));
        var1 = var1.replace("%check%", check.getVerboseName());
        var1 = var1.replace("%current_vl%", data.checkFinder(check.getClass()).getVL().getValue() + "");
        var1 = var1.replace("%ds%", check.getCheckLevel().toString());
        var1 = var1.replace("%vladd%", multiplier+"");
        var1 = var1.replace("%verbose%", verbose.equals("") ? ConfigCache.Language.getString("defaultalertverbose") : verbose);
        var1 = colorCodes(var1);

        return var1;
    }

    public static String unknownCommand(String command) {
        String message = colorCodes(ConfigCache.Language.getString("unknownCommand"));
        message = message.replace("%prefix%", prefix());
        message = message.replace("%unknown_command%", command);
        return message;
    }

    public static String colorCodes(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String prefixReplace(String txt) {
        return txt.replace("%prefix%", prefix());
    }

    public static String get(String str) {
        return (String) ConfigCache.Language.getThemeValue(str);
    }

    public static String prefix() {
        return colorCodes(get("prefix"));
    }

    public static String getString(String str) {
        return ChatHelper.colorCodes((String) ConfigCache.Language.getThemeValue(str));
    }

}
