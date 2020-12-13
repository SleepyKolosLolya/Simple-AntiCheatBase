package acname.ac.features.modules.impl;

import acname.ac.api.CheckType;
import acname.ac.api.util.DevelopmentState;
import acname.ac.features.modules.util.Check;
import acname.ac.util.Data;
import acname.ac.util.TaskUtil;
import acname.ac.util.events.global.client.ClientSwing;
import acname.ac.util.events.global.server.ServerVelocity;
import acname.ac.util.events.util.AntiCheatEvent;

public class Example extends Check {

    public int clicked = 0;

    public Example(Data data) {
        super(data, CheckType.UTIL, "Example (A)", "ExampleA", DevelopmentState.DEVELOPMENT);
    }

    @Override
    public void process(AntiCheatEvent event) {
        if (event instanceof ClientSwing) {

            if (clicked++ > 20) {
                flag("Clicked -> " + clicked, 0);
            }

        } else if (event instanceof ServerVelocity) {
            TaskUtil.taskAsync(() -> debug(" Got velocity"));
        }
    }

}
