package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientBlockPlace extends AntiCheatEvent {

    public ClientBlockPlace(PacketEvent packetEvent) {
        super(packetEvent);
    }

    public int getX() {
        return getPacketEvent().getPacket().getIntegers().read(0);
    }

    public byte getY() {
        return getPacketEvent().getPacket().getIntegers().read(1).byteValue();
    }

    public int getZ() {
        return getPacketEvent().getPacket().getIntegers().read(2);
    }


}
