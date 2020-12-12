package acname.ac.util.events.global.server;

import acname.ac.util.events.util.AntiCheatEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ServerTick extends AntiCheatEvent {

    Player player;

    public ServerTick(Player player) {
        super(player);
        this.player = player;
    }

    public @NotNull Player getPlayer() {
        return player;
    }
}
