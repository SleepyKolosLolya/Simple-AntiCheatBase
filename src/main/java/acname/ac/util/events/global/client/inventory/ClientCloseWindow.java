package acname.ac.util.events.global.client.inventory;

import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientCloseWindow extends AntiCheatEvent {

    public ClientCloseWindow(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
