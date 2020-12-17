package acname.ac.util.data;

import acname.ac.api.ACNameCheckAPI;
import acname.ac.api.util.DevelopmentState;
import acname.ac.api.util.LimitedDouble;
import acname.ac.features.checks.impl.Example;
import acname.ac.features.checks.Check;
import acname.ac.util.events.AntiCheatEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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

    public void process(AntiCheatEvent event) {

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
