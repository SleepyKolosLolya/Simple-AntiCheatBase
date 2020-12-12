package acname.ac.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectionUtils {

    private static final Pattern COMPILE = Pattern.compile(".", Pattern.LITERAL);
    public static final String version = COMPILE.matcher(Bukkit.getServer().getClass().getPackage().getName()).replaceAll(Matcher.quoteReplacement(",")).split(",")[3] + ".";

    public static Class<?> getNMSClass(String nmsClassString) {
        String name = "net.minecraft.server." + version + nmsClassString;
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float getFrictionFactor(int block) {
        try {
            Class<?> blockClass = getNMSClass("Block");
            assert blockClass != null;
            Method getBlockById = blockClass.getMethod("getById", Integer.TYPE);
            Object nmsBlock = getBlockById.invoke(blockClass, block);
            Field frictionField = nmsBlock.getClass().getField("frictionFactor");
            return frictionField.getFloat(nmsBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static float getStrength(int block, Player p) {
        try {
            Class<?> blockClass = getNMSClass("Block");
            assert blockClass != null;
            Method getBlockById = blockClass.getMethod("getById", Integer.TYPE);
            Object nmsBlock = getBlockById.invoke(blockClass, block);

            Method getPlayerHandle = p.getClass().getMethod("getHandle");
            Object nmsPlayer = getPlayerHandle.invoke(p);

            Method getWorldHandle = p.getWorld().getClass().getMethod("getHandle");
            Object nmsWorld = getWorldHandle.invoke(p.getWorld());

            Class<?> IBlockData = getNMSClass("IBlockData");
            Method getBlockData = blockClass.getMethod("getBlockData");
            Class<?> EntityHuman = getNMSClass("EntityHuman");
            Class<?> World = getNMSClass("World");
            Class<?> BlockPosition = getNMSClass("BlockPosition");

            Method getDamage = nmsBlock.getClass().getMethod("getDamage", IBlockData, EntityHuman, World, BlockPosition);
            return (float) getDamage.invoke(nmsBlock, getBlockData.invoke(nmsBlock), nmsPlayer, nmsWorld, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1000;
    }

}
