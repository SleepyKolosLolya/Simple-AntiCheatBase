package acname.ac.util.events.global.server;

import acname.ac.util.java.SMath;
import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ServerVelocity extends AntiCheatEvent {

    SMath.Velocity velocity;

    public ServerVelocity(PacketEvent packetEvent) {
        super(packetEvent);
        velocity = new SMath.Velocity(packetEvent.getPacket().getIntegers().read(1), packetEvent.getPacket().getIntegers().read(2), packetEvent.getPacket().getIntegers().read(3));
    }

    public SMath.Velocity getVelocity() {
        return velocity;
    }

}
