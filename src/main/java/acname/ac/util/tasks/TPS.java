package acname.ac.util.tasks;

import acname.ac.Global;
import org.bukkit.Bukkit;

public class TPS {

    double tps;

    public double getTps() {
        return tps;
    }

    public void init() {
        Bukkit.getServer().getScheduler().runTaskTimer(Global.PLUGIN, new Runnable() {

            long secStart;
            long secEnd;
            int ticks;

            @Override
            public void run() {
                secStart = (System.currentTimeMillis() / 1000);

                if (secStart == secEnd) {
                    ticks++;
                } else {
                    secEnd = secStart;
                    tps = (tps == 0) ? ticks : ((tps + ticks) / 2);
                    ticks = 1;
                }
            }

        }, 0, 1);
    }
}
