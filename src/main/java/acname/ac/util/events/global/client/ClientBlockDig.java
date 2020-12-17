package acname.ac.util.events.global.client;

import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class ClientBlockDig extends AntiCheatEvent {

    EnumWrappers.PlayerDigType action;
    BlockPosition location;

    public ClientBlockDig(PacketEvent packetEvent) {
        super(packetEvent);
        location = packetEvent.getPacket().getBlockPositionModifier().read(0);
        action = packetEvent.getPacket().getPlayerDigTypes().read(0);
    }

    public EnumWrappers.PlayerDigType getAction() {
        return action;
    }

    public BlockPosition getLocation() {
        return location;
    }
}
