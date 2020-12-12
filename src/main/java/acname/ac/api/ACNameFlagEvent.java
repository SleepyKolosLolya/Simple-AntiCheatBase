package acname.ac.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ACNameFlagEvent extends Event implements Cancellable {

    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Player player;
    private final ACNameCheckAPI checkAPI;
    private String verbose;

    public String getVerbose() {
        return verbose;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    private float multiplier;
    private boolean cancelled;

    public ACNameFlagEvent(Player player, ACNameCheckAPI checkAPI, String verbose, Number multiplier) {
        this.player = player;
        this.checkAPI = checkAPI;
        this.verbose = verbose;
        this.multiplier = (float) multiplier;
    }

    public ACNameFlagEvent(Player player, ACNameCheckAPI checkAPI) {
        this.player = player;
        this.checkAPI = checkAPI;
        this.verbose = "";
        this.multiplier = 1;
    }

    public ACNameFlagEvent(Player player, ACNameCheckAPI checkAPI, String verbose) {
        this.player = player;
        this.checkAPI = checkAPI;
        this.verbose = verbose;
        this.multiplier = 1;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return ACNameFlagEvent.handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public ACNameCheckAPI getCheckAPI() {
        return checkAPI;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
