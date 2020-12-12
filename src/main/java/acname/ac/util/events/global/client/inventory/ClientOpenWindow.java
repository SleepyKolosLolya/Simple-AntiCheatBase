package acname.ac.util.events.global.client.inventory;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientOpenWindow extends AntiCheatEvent {

    public ClientOpenWindow(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
