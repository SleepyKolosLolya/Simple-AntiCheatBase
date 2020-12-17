package acname.ac.util.events;

import acname.ac.Global;
import acname.ac.util.world.LocationUtil;
import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.global.client.*;
import acname.ac.util.events.global.client.inventory.ClientClickWindow;
import acname.ac.util.events.global.client.inventory.ClientCloseWindow;
import acname.ac.util.events.global.client.inventory.ClientOpenWindow;
import acname.ac.util.events.global.server.*;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.ArrayListMultimap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class ProtocolLibListener {

    private final static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    public static HashMap<Player, ArrayListMultimap<Short, Long>> ticksSent = new HashMap<>();
    final HashMap<UUID, LocationUtil> locations;

    public ProtocolLibListener() {
        locations = new HashMap<>();
        init();

        tickProcessor19();
    }

    public static void handle(AntiCheatEvent event) {
        PluginUtils.handle(event);
    }

    private void init() {

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                int id = event.getPacket().getIntegers().read(0);
                // Some checks can be broken bc of this shit so yes you need to kick player if (id <= 0)
                if (id <= 0) {
                    Bukkit.getScheduler().runTask(Global.PLUGIN, () -> event.getPlayer().kickPlayer("null"));
                    return;
                }
                handle(new ClientUseEntity(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.FLYING, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPlayer() == null) return;
                UUID uid = event.getPlayer().getUniqueId();
                ClientFlying flying = new ClientFlying(event, locations.get(uid));
                locations.put(uid, new LocationUtil(event.getPlayer().getWorld(), flying.getTo().getX(), flying.getTo().getY(), flying.getTo().getZ(), flying.getTo().getYaw(), flying.getTo().getPitch()));
                handle(flying);
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.ENTITY_ACTION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientAction(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.TRANSACTION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientTransaction(event));
                short id = event.getPacket().getShorts().read(0);

                if (ticksSent.get(event.getPlayer()).containsKey(id)) {
                    long sentTime = ticksSent.get(event.getPlayer()).get(id).get(0);

                    double delay = (System.nanoTime() - sentTime) / 1000000D;

                    ticksSent.get(event.getPlayer()).remove(id, sentTime);
                    PluginUtils.getDataByUUID(event.getPlayer().getUniqueId()).setPing((int) delay);
                    handle(new ClientTick(event.getPlayer(), delay));
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.TRANSACTION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerTransaction(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.ABILITIES) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientAbilities(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.ABILITIES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerAbilities(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientUseEntity(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.ARM_ANIMATION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientSwing(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientBlockDig(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.ENTITY_VELOCITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPlayer().getEntityId() == event.getPacket().getIntegers().read(0)) {
                    handle(new ServerVelocity(event));
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientKeepAlive(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.KEEP_ALIVE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerKeepAlive(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.OPEN_WINDOW) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ClientOpenWindow(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.CLOSE_WINDOW) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientCloseWindow(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.WINDOW_CLICK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientClickWindow(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.POSITION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerTeleport(event));
            }
        });

        if (PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_12_R1)) {
            protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.TELEPORT_ACCEPT) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    handle(new ClientTeleportAccept(event));
                }
            });
        }

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.BLOCK_PLACE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientBlockPlace(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.USE_ITEM) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientUseItem(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.HELD_ITEM_SLOT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientSlotChange(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST,
                PacketType.Play.Server.REL_ENTITY_MOVE,
                PacketType.Play.Server.REL_ENTITY_MOVE_LOOK,
                PacketType.Play.Server.ENTITY_LOOK
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerEntityRelMove(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Server.ENTITY_TELEPORT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handle(new ServerEntityTeleport(event));
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(Global.PLUGIN, ListenerPriority.HIGHEST, PacketType.Play.Client.SETTINGS) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                handle(new ClientSettings(event));
            }
        });

    }

    /**
     * Can be really useful for 1.9+ servers
     *
     * @author Maxsimus
     */
    public void tickProcessor19() {
        Bukkit.getScheduler().runTaskAsynchronously(Global.PLUGIN, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                handle(new ServerTick(p));

                //Client tick system for 1.9+

                PacketContainer packet = new PacketContainer(PacketType.Play.Server.TRANSACTION);
                short id = PluginUtils.getDataByUUID(p.getUniqueId()).genTransactionID();

                packet.getIntegers().write(0, 0);
                packet.getShorts().write(0, id);
                packet.getBooleans().write(0, false);

                try {
                    protocolManager.sendServerPacket(p, packet);
                    ticksSent.get(p).put(id, System.nanoTime());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}