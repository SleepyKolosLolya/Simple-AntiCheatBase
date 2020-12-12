package acname.ac.util.events.global.client.inventory;

import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.inventory.ItemStack;

public class ClientClickWindow extends AntiCheatEvent {

    ItemStack item;

    public ClientClickWindow(PacketEvent packetEvent) {
        super(packetEvent);
        item = packetEvent.getPacket().getItemModifier().read(0);
    }

    public ItemStack getItem() {
        return item;
    }

}
