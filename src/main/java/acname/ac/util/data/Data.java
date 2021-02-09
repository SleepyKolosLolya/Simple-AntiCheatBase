package acname.ac.util.data;

import acname.ac.features.checks.impl.Example;
import acname.ac.features.checks.Check;
import acname.ac.util.events.AntiCheatEvent;
import acname.ac.util.events.global.client.ClientAction;
import acname.ac.util.events.global.client.ClientFlying;
import acname.ac.util.events.global.server.ServerVelocity;
import acname.ac.util.java.SMath;
import acname.ac.util.world.LocationUtil;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Data {

    public final Check[] checks = {
            new Example(this)
    };

    final OfflinePlayer player;
    final Executor executor = Executors.newSingleThreadExecutor();
    public boolean alerts;
    short lastID = Short.MIN_VALUE;
    int ping;
    Runnable lastTask;

    public Data(OfflinePlayer player) {
        this.player = player;
    }

    public Player getPlayer() {
        return (Player) player;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }


    public int groundTicks = 0;
    public int airTicks = 0;
    public int ticksSinceSprint = 0;
    public int underBlocksTicks = 0;
    public SMath.Velocity lastVelocity = null;
    public long lastVelocityMS = -1;
    public long lastServerTeleport = -1;
    public boolean isSprinting = false;
    public boolean isSneaking = false;

    public void process(final AntiCheatEvent event) {
        if (event instanceof ClientFlying) {

            final ClientFlying cf = (ClientFlying) event;
            final LocationUtil to = cf.getTo();
            final boolean isNearGround = to.isNearGround();

            if (!isSprinting) ticksSinceSprint++;

            groundTicks = isNearGround ? groundTicks + 1 : 0;
            airTicks = !isNearGround ? airTicks + 1 : 0;
            underBlocksTicks = to.isUnderBlock() ? underBlocksTicks + 1 : 0;

        } else if (event instanceof ServerVelocity) {
            this.lastVelocityMS = System.currentTimeMillis();
            this.lastVelocity = ((ServerVelocity) event).getVelocity();
        } else if (event instanceof ClientAction) {
            isSprinting = ((ClientAction) event).getAction().equals(EnumWrappers.PlayerAction.START_SPRINTING);
            isSneaking = ((ClientAction) event).getAction().equals(EnumWrappers.PlayerAction.START_SNEAKING);
        }
    }

    public boolean isTeleporting() {
        return 175 + ping <= System.currentTimeMillis() - lastServerTeleport;
    }

    public boolean isTakingVelocity() {
        return (175 + ping + getMaxVelocityTicks() * 50L) <= System.currentTimeMillis() - lastVelocityMS;
    }

    int getMaxVelocityTicks() {
        float result = (float) Math.abs(lastVelocity.getVelocityXZ());
        int ticks = 0;
        do {
            result -= .02; // SpeedInAir value
            result *= .91; // Air Friction
            ticks++;
        } while (result > .01);

        return ticks;
    }

    public double getWalkSpeed() {
        double speed = getPlayer().getWalkSpeed() / .91;
        speed *= (ticksSinceSprint < 2 || isSprinting) ? 1.3 : 1;
        speed *= (isSneaking) ? .3 : 1;
        speed *= (getPlayer().hasPotionEffect(PotionEffectType.SPEED)) ? 1.2 * getPotionEffectAmplifier("SPEED") : 1;
        speed -= (getPlayer().hasPotionEffect(PotionEffectType.SLOW)) ? speed * .15 * getPotionEffectAmplifier("SLOW") : 0;

        return speed;
    }

    public int getPotionEffectAmplifier(String name) {
        for (PotionEffect e : getPlayer().getActivePotionEffects())
            if (e.getType().getName().equals(name)) return e.getAmplifier() + 1;
        return 0;
    }


    public short genTransactionID() {
        if (lastID >= Short.MAX_VALUE - 1) {
            lastID = Short.MIN_VALUE;
        }
        return ++lastID;
    }

    public void handle(AntiCheatEvent event) {
        this.lastTask = () -> {
            try {
                process(event);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            for (Check acCheck : this.checks) {
                try {
                    if (acCheck.isEnabled()) acCheck.process(event);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        executor.execute(lastTask);
    }

    public Check checkFinder(Class<?> check) {
        for (Check checker : checks) {
            if (checker.getClass().getName().equals(check.getName())) return checker;
        }
        return null;
    }

    public Check checkFinder(String checkName) {
        for (Check checker : checks) {
            if (checker.getConfigName().equalsIgnoreCase(checkName)) return checker;
        }
        return null;
    }

}
