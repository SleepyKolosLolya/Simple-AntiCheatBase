package acname.ac.util.events.global.server;

import acname.ac.util.world.WorldUtil;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Entity;

public class ServerEntityTeleport extends AntiCheatEvent {

    final PacketType pt;
    final Entity entity;
    final boolean onGround;
    final float yaw;
    final float pitch;
    final int posX;
    final int posY;
    final int posZ;

    public ServerEntityTeleport(PacketEvent packetEvent) {
        super(packetEvent);

        pt = packetEvent.getPacketType();

        entity = WorldUtil.findEntity(packetEvent.getPlayer().getWorld(), packetEvent.getPacket().getIntegers().read(0));

        onGround = getPacket().getBooleans().read(0);
        yaw = (packetEvent.getPacket().getBytes().read(0) * 360F) / 256F;
        pitch = (packetEvent.getPacket().getBytes().read(1) * 360F) / 256F;

        posX = getPacket().getIntegers().read(0);
        posY = getPacket().getIntegers().read(1);
        posZ = getPacket().getIntegers().read(2);

    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public double getPosX() {
        return posX / 32.0D;
    }

    public double getPosY() {
        return posY / 32.0D;
    }

    public double getPosZ() {
        return posZ / 32.0D;
    }
}
