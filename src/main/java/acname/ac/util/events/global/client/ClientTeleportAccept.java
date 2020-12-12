package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientTeleportAccept extends AntiCheatEvent {

    public ClientTeleportAccept(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
