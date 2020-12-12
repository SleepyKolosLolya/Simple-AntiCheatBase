package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class ClientUseItem extends AntiCheatEvent {

    BlockPosition location;
    EnumWrappers.Direction face;

    public ClientUseItem(PacketEvent e) {
        super(e);

        location = getPacket().getBlockPositionModifier().read(0);
        face = getPacket().getDirections().read(0);
    }

    public BlockPosition getLocation() {
        return location;
    }

    public EnumWrappers.Direction getFace() {
        return face;
    }
}
