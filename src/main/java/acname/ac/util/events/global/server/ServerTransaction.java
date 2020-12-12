package acname.ac.util.events.global.server;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ServerTransaction extends AntiCheatEvent {

    private final short id;

    public ServerTransaction(PacketEvent e) {
        super(e);
        id = packetEvent.getPacket().getShorts().read(0);
    }

    public short getId() {
        return id;
    }

}
