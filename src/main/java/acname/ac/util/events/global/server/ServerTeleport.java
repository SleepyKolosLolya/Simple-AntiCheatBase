package acname.ac.util.events.global.server;

import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ServerTeleport extends AntiCheatEvent {

    public ServerTeleport(PacketEvent packetEvent) {
        super(packetEvent);
        PluginUtils.getDataByUUID(getPlayer().getUniqueId()).lastServerTeleport = System.currentTimeMillis();
    }

}
