package acname.ac.util.data;

import acname.ac.features.checks.Check;
import acname.ac.Global;
import acname.ac.util.chat.ChatHelper;
import acname.ac.util.events.AntiCheatEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PluginUtils implements Listener {

    private static final Map<UUID, Data> dataMap;

    static {
        dataMap = new HashMap<>();
    }

    public static ServerVersion getVersion() {
        return ServerVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
    }

    public static void flag(final Check check, final float multiplier, final Data data, final String verbose) {

        TextComponent msg = ChatHelper.generateVerboseLog(data, check, verbose, multiplier);

        for (Player on : Bukkit.getOnlinePlayers()) {
            if (on.hasPermission("abc.alerts")) {
                if (dataMap.get(on.getUniqueId()).alerts) on.spigot().sendMessage(msg);
            }
        }

        Check c = data.checkFinder(check.getClass());

        c.getVL().addValue(multiplier);

        if ((int) check.getVL().getValue() == check.getBanVL()) {
            Bukkit.getScheduler().runTask(Global.PLUGIN, () -> {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), check.getPunishment());
            });
        }

    }

    public static void handle(AntiCheatEvent event) {
        Player player = event.getPlayer();
        getDataByUUID(player.getUniqueId()).handle(event);
    }

    public static Data getDataByUUID(UUID id) {
        if (!dataMap.containsKey(id)) {
            dataMap.put(id, new Data(Bukkit.getPlayer(id)));
        }
        return dataMap.get(id);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID player = event.getPlayer().getUniqueId();

        dataMap.put(player, new Data(event.getPlayer()));
    }

    public enum ServerVersion {
        v1_8_R3,
        v1_12_R1
    }

}
