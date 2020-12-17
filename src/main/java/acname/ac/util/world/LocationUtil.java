package acname.ac.util.world;

import acname.ac.util.bukkit.ReflectionUtils;
import acname.ac.util.data.PluginUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;


public class LocationUtil {

    // NMS
    private Method getCubes;
    private Method getCubes1_12;
    Class<?> aabb = ReflectionUtils.getNMSClass("AxisAlignedBB");

    private World world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public LocationUtil(World world, final double x, final double y, final double z, final float yaw, final float pitch) {

        try {
            if (PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_8_R3)) {
                getCubes = Objects.requireNonNull(ReflectionUtils.getNMSClass("World")).getMethod("a", aabb);
            } else {
                getCubes1_12 = Objects.requireNonNull(
                        ReflectionUtils.getNMSClass(
                        "World")
                ).getMethod("getCubes", ReflectionUtils.getNMSClass("Entity"), aabb);
            }
        } catch (NoSuchMethodException err) {
            err.printStackTrace();
        }

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LocationUtil(World world, final double x, final double y, final double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public LocationUtil(Location location) {
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Block getBlock() {
        return WorldUtil.getBlockAsync(this);
    }

    public Location toBukkit() {
        return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public boolean hasCollision() {
        try {
            Method getWorldHandle = world.getClass().getMethod("getHandle");
            Object nmsWorld = getWorldHandle.invoke(world);
            Object box = aabb.getConstructor(double.class, double.class, double.class, double.class, double.class, double.class)
                    .newInstance(
                            getX() - .33,
                            getY(),
                            getZ() - .33,
                            getX() + .33,
                            getY() + 2,
                            getZ() + .33
                    );

            return PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_8_R3) ?
                ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes, nmsWorld, box)).size() != 0 :
                    ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes1_12, nmsWorld, null, box)).size() != 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    boolean isAgainstABlock(double expand) {
        try {
            Method getWorldHandle = world.getClass().getMethod("getHandle");
            Object nmsWorld = getWorldHandle.invoke(world);
            Object box = aabb.getConstructor(double.class, double.class, double.class, double.class, double.class, double.class)
                    .newInstance(
                            getX() - .3 - expand,
                            getY(),
                            getZ() - .3 - expand,
                            getX() + .3 + expand,
                            getY() + 1.8,
                            getZ() + .3 + expand
                    );

            return PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_8_R3) ?
                    ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes, nmsWorld, box)).size() > 0 :
                    ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes1_12, nmsWorld, null, box)).size() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    public boolean isNearGround() {
        try {
            Method getWorldHandle = world.getClass().getMethod("getHandle");
            Object nmsWorld = getWorldHandle.invoke(world);
            Object box = aabb.getConstructor(double.class, double.class, double.class, double.class, double.class, double.class)
                    .newInstance(
                            getX() - .3,
                            getY() - .5001,
                            getZ() - .3,
                            getX() + .3,
                            getY(),
                            getZ() + .3
                    );

            return PluginUtils.getVersion().equals(PluginUtils.ServerVersion.v1_8_R3) ?
                    ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes, nmsWorld, box)).size() > 0 :
                    ((Collection<?>) ReflectionUtils.getInvokedMethod(getCubes1_12, nmsWorld, null, box)).size() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public String toString() {
        return "LocationUtil{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", world=" + world +
                '}';
    }
}
