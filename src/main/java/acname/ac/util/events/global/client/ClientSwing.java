package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientSwing extends AntiCheatEvent {

    public ClientSwing(PacketEvent packetEvent) {
        super(packetEvent);
    }

}
