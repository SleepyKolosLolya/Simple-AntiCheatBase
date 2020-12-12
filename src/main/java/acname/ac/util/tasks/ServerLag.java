package acname.ac.util.tasks;

public class ServerLag {

    long timeBehind = 0;
    long lastTimeBehind = 0;
    long lastTimeReset = 0;
    int times = 0;

    public int getServerLag() {
        long newTimeBehind = (System.nanoTime() - lastTimeBehind - 50000000) / 1000000;
        if (newTimeBehind > timeBehind) {
            timeBehind = newTimeBehind;

            if (newTimeBehind > 75) {
                lastTimeReset = System.currentTimeMillis();
            }
        }
        return (int) timeBehind;
    }

    public void init() {
        long newTimeBehind = (System.nanoTime() - lastTimeBehind - 50000000) / 1000000;

        if (newTimeBehind < 0) {
            newTimeBehind = 0;
        }

        if (lastTimeBehind == 0) {
            newTimeBehind = 0;
        }

        if (newTimeBehind > 75 && newTimeBehind >= timeBehind) {
            if (times++ > 0) {
                // newTimeBehind == skipped ms by server
            }
            lastTimeReset = System.currentTimeMillis();
        } else {
            times = 0;
        }

        if (newTimeBehind > timeBehind) {
            timeBehind = newTimeBehind;

            if (newTimeBehind > 75) {
                lastTimeReset = System.currentTimeMillis();
            }
        } else if (System.currentTimeMillis() - lastTimeReset > 1000) {
            timeBehind = (timeBehind * 5 + newTimeBehind) / 6;
        }

        lastTimeBehind = System.nanoTime();

    }


}
