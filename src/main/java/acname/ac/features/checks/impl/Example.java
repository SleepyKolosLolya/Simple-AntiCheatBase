package acname.ac.features.checks.impl;

import acname.ac.api.CheckType;
import acname.ac.api.util.DevelopmentState;
import acname.ac.features.checks.Check;
import acname.ac.util.bukkit.TaskUtil;
import acname.ac.util.data.Data;
import acname.ac.util.events.AntiCheatEvent;
import acname.ac.util.events.global.client.ClientSwing;
import acname.ac.util.events.global.server.ServerVelocity;

public class Example extends Check {

    public int clicked = 0;

    public Example(final Data data) {
        super(data, CheckType.UTIL, "Example (A)", "ExampleA", DevelopmentState.DEVELOPMENT);
        setBanVL(getBanVL());
    }

    @Override
    public void process(final AntiCheatEvent event) {
        if (event instanceof ClientSwing) {

            if (clicked++ == 20) {
                flag("Clicked -> " + clicked, 0);
            } else if (clicked == 40) {
                flag(0);
                clicked = 0;
            }

        } else if (event instanceof ServerVelocity) {
            debug("vel=" + ((ServerVelocity) event).getVelocity().toString());
        }
    }

}
