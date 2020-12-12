package acname.ac.util.events.global.server;

import acname.ac.util.MathUtil;
import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ServerVelocity extends AntiCheatEvent {

    MathUtil.Velocity velocity;

    public ServerVelocity(PacketEvent packetEvent) {
        super(packetEvent);
        velocity = new MathUtil.Velocity(packetEvent.getPacket().getIntegers().read(1), packetEvent.getPacket().getIntegers().read(2), packetEvent.getPacket().getIntegers().read(3));
    }

    public MathUtil.Velocity getVelocity() {
        return velocity;
    }

}
