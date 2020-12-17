package acname.ac.util.events.global.client;

import acname.ac.util.events.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;

public class ClientSlotChange extends AntiCheatEvent {

    int slot;

    public ClientSlotChange(PacketEvent e) {
        super(e);

        slot = getPacket().getIntegers().read(0);
    }

    public int getSlot() {
        return slot;
    }

}
