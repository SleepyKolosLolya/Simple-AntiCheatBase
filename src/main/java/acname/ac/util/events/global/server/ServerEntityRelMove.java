package acname.ac.util.events.global.server;

import acname.ac.util.world.WorldUtil;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Entity;

public class ServerEntityRelMove extends AntiCheatEvent {

    final PacketType pt;
    final Entity entity;
    float yaw = 0;
    float pitch = 0;
    byte deltaX = 0;
    byte deltaY = 0;
    byte deltaZ = 0;

    public ServerEntityRelMove(PacketEvent packetEvent) {
        super(packetEvent);

        pt = packetEvent.getPacketType();

        entity = WorldUtil.findEntity(packetEvent.getPlayer().getWorld(), packetEvent.getPacket().getIntegers().read(0));

        if (packetEvent.getPacketType().equals(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK) ||
                packetEvent.getPacketType().equals(PacketType.Play.Server.REL_ENTITY_MOVE)) {
            deltaX = getPacket().getBytes().read(0);
            deltaY = getPacket().getBytes().read(1);
            deltaZ = getPacket().getBytes().read(2);
        }

        if (packetEvent.getPacketType().equals(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK) ||
                packetEvent.getPacketType().equals(PacketType.Play.Server.ENTITY_LOOK)) {
            yaw = (packetEvent.getPacket().getBytes().read(0) * 360F) / 256F;
            pitch = (packetEvent.getPacket().getBytes().read(0) * 360F) / 256F;
        } else if (entity != null) {
            yaw = entity.getLocation().getYaw();
            pitch = entity.getLocation().getPitch();
        }

    }

    public Entity getEntity() {
        return entity;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public double getDeltaX() {
        return deltaX / 32.0D;
    }

    public double getDeltaY() {
        return deltaY / 32.0D;
    }

    public double getDeltaZ() {
        return deltaZ / 32.0D;
    }


}
