package acname.ac.util.world;

import acname.ac.util.world.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.Objects;

public final class WorldUtil {

    public static Entity findEntity(World w, int id) {
        for (Entity e : w.getEntities()) {
            if (e.getEntityId() == id) return e;
        }
        return null;
    }

    public static Block getBlockAsync(Location loc) throws NullPointerException {
        if (loc == null) throw new NullPointerException("Location can't be null");
        if (Objects.requireNonNull(loc.getWorld()).isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4))
            return loc.getBlock();
        return null;
    }

    public static Block getBlockAsync(LocationUtil loc) throws NullPointerException {
        if (loc == null) throw new NullPointerException("LocationUtil can't be null");
        return getBlockAsync(loc.toBukkit());
    }

    public static Block getTargetedBlock(Player player, Integer range) {
        BlockIterator bi = new BlockIterator(player, range);
        Block lastBlock = bi.next();
        while (bi.hasNext()) {
            lastBlock = bi.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }
        return lastBlock;
    }


}
