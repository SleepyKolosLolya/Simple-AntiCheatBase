package acname.ac.util.events.global.server;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ServerTeleport extends AntiCheatEvent {

    public ServerTeleport(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
