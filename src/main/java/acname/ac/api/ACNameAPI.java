package acname.ac.api;

import acname.ac.api.util.LimitedDouble;
import acname.ac.Global;
import acname.ac.util.data.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ACNameAPI {

    public static String BYPASS_PERMISSION = "acb.bypass";

    public static String getVersion() {
        return Global.PLUGIN.getDescription().getVersion();
    }

    public static String getBypassPermission() {
        return BYPASS_PERMISSION;
    }

    public static LimitedDouble<? extends Number> getVLManager(Player pl, ACNameCheckAPI checkAPI) {
        return PluginUtils.getDataByUUID(pl.getUniqueId()).checkFinder(checkAPI.check.getConfigName()).getVL();
    }

    public static void flag(boolean ignoreBypass, Player pl, ACNameCheckAPI checkAPI, Number vl, String verbose) {

        ACNameFlagEvent flagEvent = new ACNameFlagEvent(pl, checkAPI, verbose, vl);

        Bukkit.getServer().getPluginManager().callEvent(flagEvent);
        if ((ignoreBypass || !pl.hasPermission(getBypassPermission()) || Global.getConfig().getBoolean("bypassIgnore")) && !flagEvent.isCancelled()) {
            PluginUtils.flag(checkAPI.check, vl, PluginUtils.getDataByUUID(pl.getUniqueId()), verbose);
        }

    }


}
