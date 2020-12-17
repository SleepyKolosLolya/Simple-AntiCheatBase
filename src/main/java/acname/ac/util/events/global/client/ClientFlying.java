package acname.ac.util.events.global.client;

import acname.ac.util.data.Data;
import acname.ac.util.world.LocationUtil;
import acname.ac.util.java.MathUtil;
import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.comphenix.protocol.PacketType.Play.Client.*;

public class ClientFlying extends AntiCheatEvent {

    LocationUtil from;
    LocationUtil to;
    boolean isOnGround;
    boolean hasLook;
    boolean hasPos;
    double deltaXZ;
    double deltaY;
    double deltaX;
    double deltaZ;
    float deltaPitch;
    float deltaYaw;
    World world;
    Player player;

    public ClientFlying(PacketEvent packetEvent, LocationUtil fromInput) {
        super(packetEvent);
        this.player = packetEvent.getPlayer();

        world = player.getWorld();
        if (fromInput == null) {
            Location local_from = packetEvent.getPlayer().getLocation();
            this.from = new LocationUtil(local_from);
        } else {
            this.from = fromInput;
        }

        if (packetEvent.getPacketType().equals(FLYING)) {
            to = from;
        } else if (packetEvent.getPacketType().equals(POSITION)) {
            to = new LocationUtil(world,
                    packetEvent.getPacket().getDoubles().read(0),
                    packetEvent.getPacket().getDoubles().read(1),
                    packetEvent.getPacket().getDoubles().read(2),
                    from.getYaw(),
                    from.getPitch()
            );
            hasPos = true;
        } else if (packetEvent.getPacketType().equals(POSITION_LOOK)) {
            to = new LocationUtil(world,
                    packetEvent.getPacket().getDoubles().read(0),
                    packetEvent.getPacket().getDoubles().read(1),
                    packetEvent.getPacket().getDoubles().read(2),
                    packetEvent.getPacket().getFloat().read(0),
                    packetEvent.getPacket().getFloat().read(1)
            );
            hasPos = true;
            hasLook = true;
        } else if (packetEvent.getPacketType().equals(LOOK)) {
            to = new LocationUtil(world,
                    from.getX(),
                    from.getY(),
                    from.getZ(),
                    packetEvent.getPacket().getFloat().read(0),
                    packetEvent.getPacket().getFloat().read(1)
            );
            hasLook = true;
        }

        isOnGround = packetEvent.getPacket().getBooleans().read(0);
        deltaX = to.getX() - from.getX();
        deltaZ = to.getZ() - from.getX();
        deltaXZ = MathUtil.hypot(deltaX, deltaZ);
        deltaY = to.getY() - from.getY();

        deltaPitch = to.getPitch() - from.getPitch();
        deltaYaw = Math.abs(to.getYaw() - from.getYaw());
    }

    public Data getData() {
        return PluginUtils.getDataByUUID(player.getUniqueId());
    }

    public LocationUtil getFrom() {
        return from;
    }

    public LocationUtil getTo() {
        return to;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public boolean isHasLook() {
        return hasLook;
    }

    public boolean isHasPos() {
        return hasPos;
    }

    public double getDeltaXZ() {
        return deltaXZ;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public float getDeltaPitch() {
        return deltaPitch;
    }

    public float getDeltaYaw() {
        return deltaYaw;
    }

    public World getWorld() {
        return world;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

}
