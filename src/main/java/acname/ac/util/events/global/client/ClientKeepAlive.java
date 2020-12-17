package acname.ac.util.events.global.client;

import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientKeepAlive extends AntiCheatEvent {

    long id;

    public ClientKeepAlive(PacketEvent packetEvent) {
        super(packetEvent);
        if (PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_8_R3)) {
            id = packetEvent.getPacket().getIntegers().read(0);
        } else {
            id = packetEvent.getPacket().getLongs().read(0);
        }
    }

    public long getId() {
        return id;
    }
}
