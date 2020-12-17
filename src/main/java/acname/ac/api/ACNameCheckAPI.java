package acname.ac.api;

import acname.ac.api.util.DevelopmentState;
import acname.ac.api.util.LimitedDouble;
import acname.ac.features.checks.Check;
import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.AntiCheatEvent;
import org.bukkit.entity.Player;

public class ACNameCheckAPI {

    final Check check;

    public ACNameCheckAPI(Check check) {
        this.check = check;
    }

    public Player getPlayer() {
        return check.getData().getPlayer();
    }

    public LimitedDouble<? extends Number> getVL() {
        return check.getVL();
    }

    public String getChatName() {
        return check.getVerboseName();
    }

    public String getConfigLocation() {
        return check.getConfigLocation();
    }

    public CheckType getCheckType() {
        return check.getCheckType();
    }

}
