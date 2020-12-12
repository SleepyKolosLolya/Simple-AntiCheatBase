package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Entity;

public class ClientUseEntity extends AntiCheatEvent {

    EnumWrappers.EntityUseAction action;
    int targetID;
    Entity target;

    public ClientUseEntity(PacketEvent packetEvent) {
        super(packetEvent);
        action = packetEvent.getPacket().getEntityUseActions().read(0);
        targetID = packetEvent.getPacket().getIntegers().read(0);
        target = packetEvent.getPacket().getEntityModifier(packetEvent.getPlayer().getWorld()).read(0);
    }

    public EnumWrappers.EntityUseAction getAction() {
        return action;
    }

    public int getTargetID() {
        return targetID;
    }

    public Entity getTarget() {
        return target;
    }


}
