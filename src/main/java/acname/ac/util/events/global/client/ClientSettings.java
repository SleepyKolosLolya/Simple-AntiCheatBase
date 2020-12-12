package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientSettings extends AntiCheatEvent {

    public ClientSettings(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
