package acname.ac.util.events.global.client;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientAbilities extends AntiCheatEvent {

    private final boolean invulnerable;
    private final boolean flying;
    private final boolean allowFlying;
    private final boolean instantBreak;

    public ClientAbilities(PacketEvent packetEvent) {
        super(packetEvent);

        invulnerable = packetEvent.getPacket().getBooleans().read(0);
        flying = packetEvent.getPacket().getBooleans().read(1);
        allowFlying = packetEvent.getPacket().getBooleans().read(2);
        instantBreak = packetEvent.getPacket().getBooleans().read(3);
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isAllowFlying() {
        return allowFlying;
    }

    public boolean isInstantBreak() {
        return instantBreak;
    }
}
