package acname.ac.util.events.global.client;

import acname.ac.util.events.AntiCheatEvent;
import org.bukkit.entity.Player;

public class ClientTick extends AntiCheatEvent {

    private final double ping;

    public ClientTick(Player player, double delay) {
        super(player);
        this.ping = delay;
    }

    public double getPing() {
        return ping;
    }

}
