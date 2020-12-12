package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientTransaction extends AntiCheatEvent {

    private final short id;

    public ClientTransaction(PacketEvent e) {
        super(e);
        id = packetEvent.getPacket().getShorts().read(0);
    }

    public short getId() {
        return id;
    }

}
