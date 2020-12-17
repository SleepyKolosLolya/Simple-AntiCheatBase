package acname.ac.util.events;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AntiCheatEvent {

    public final PacketEvent packetEvent;
    public final Player player;

    public AntiCheatEvent(PacketEvent packetEvent) {
        this.packetEvent = packetEvent;
        this.player = packetEvent.getPlayer();
    }

    public AntiCheatEvent(Player player) {
        this.packetEvent = null;
        this.player = player;
    }

    public @Nullable PacketEvent getPacketEvent() {
        return packetEvent;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public PacketContainer getPacket() {
        return packetEvent.getPacket();
    }
}
