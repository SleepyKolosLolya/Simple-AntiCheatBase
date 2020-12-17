package acname.ac.util.events.global.client;

import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class ClientAction extends AntiCheatEvent {

    private final EnumWrappers.PlayerAction action;

    public ClientAction(PacketEvent packetEvent) {
        super(packetEvent);
        this.action = packetEvent.getPacket().getPlayerActions().read(0);
    }

    public EnumWrappers.PlayerAction getAction() {
        return action;
    }

}
