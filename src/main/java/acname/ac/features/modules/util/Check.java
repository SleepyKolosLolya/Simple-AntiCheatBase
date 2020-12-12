package acname.ac.features.modules.util;

import acname.ac.api.ACNameAPI;
import acname.ac.api.ACNameCheckAPI;
import acname.ac.api.CheckType;
import acname.ac.api.util.DevelopmentState;
import acname.ac.api.util.LimitedDouble;
import acname.ac.plugin.Global;
import acname.ac.util.ChatManager;
import acname.ac.util.Data;
import acname.ac.util.events.util.AntiCheatEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Check extends Global {

    private static final Pattern VERBOSE = Pattern.compile("%verbose%", Pattern.LITERAL);
    final ConcurrentHashMap<Short, AntiCheatEvent> awaiting;
    private final Data data;
    private final CheckType checkType;
    private final String verboseName;
    @Deprecated
    private final String configName;
    private final DevelopmentState developmentState;
    public boolean debug;
    private LimitedDouble<Float> VL;

    public Check(Data data, CheckType checkType, String verboseName, String configName, DevelopmentState developmentState) {
        this.data = data;
        this.checkType = checkType;
        this.verboseName = verboseName;
        this.configName = configName;
        this.developmentState = developmentState;
        awaiting = new ConcurrentHashMap<>();
        setBanVL(getBanVL());
    }

    public AntiCheatEvent isReceived(short id) {
        return awaiting.remove(id);
    }

    public short await(AntiCheatEvent ace) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TRANSACTION);
        short id = getData().genTransactionID();

        packet.getIntegers().write(0, 0);
        packet.getShorts().write(0, id);
        packet.getBooleans().write(0, false);

        try {
            Global.pm.sendServerPacket(getData().getPlayer(), packet);
            awaiting.put(id, ace);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public ACNameCheckAPI getCheckAPI() {
        return new ACNameCheckAPI(this);
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public String getVerboseName() {
        return verboseName;
    }

    public String getConfigName() {
        return configName;
    }

    public DevelopmentState getCheckLevel() {
        return developmentState;
    }

    public LimitedDouble<? extends Number> getVL() {
        return VL;
    }

    public void setVL(LimitedDouble<Float> VL) {
        this.VL = VL;
    }

    public abstract void process(AntiCheatEvent event);

    public void flag(String verbose) {
        flag(verbose, 1);
    }

    public void flag() {
        flag("", 1);
    }

    public void flag(String verbose, double multiplier) {
        ACNameAPI.flag(false, this.data.getPlayer(), this.getCheckAPI(), multiplier, verbose);
    }

    public Data getData() {
        return data;
    }

    public String getConfigLocation() {
        return checkType + "." + configName;
    }

    public String getPunishment() {
        return Global.getConfig().getString(getConfigLocation() + ".punishment");
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void updateDebug() {
        debug = !debug;
    }

    public void debug(String verbose) {
        if (debug) {
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (on.hasPermission("acb.debug")) {
                    on.sendMessage(VERBOSE.matcher(
                            ChatManager.colorCodes(
                                    ChatManager.LanguageAccess.getString("debug")))
                            .replaceAll(Matcher.quoteReplacement(verbose))
                    );
                }
            }
        }
    }

    public int getBanVL() {
        return Global.getConfig().getInt(getConfigLocation() + ".banVL");
    }

    public void setBanVL(double banVL) {
        VL = new LimitedDouble<>(0F, banVL);
    }

    public boolean isEnabled() {
        return Global.getConfig().getBoolean(getConfigLocation() + ".enabled");
    }

}
