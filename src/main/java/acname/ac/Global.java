package acname.ac;

import acname.ac.util.config.CustomConfig;
import acname.ac.util.tasks.ServerLag;
import acname.ac.util.tasks.TPS;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Global {

    public static ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
    public static Thread MAIN_THREAD;
    public static String FOLDER_NAME;
    public static TPS TPS_COUNTER;
    public static ServerLag SERVER_LAG;
    public static Plugin PLUGIN;
    public static CustomConfig LANGUAGE;
    public static CustomConfig CONFIG;
    public static Logger LOGGER;

    public static FileConfiguration getConfig() {
        return CONFIG.get();
    }

    public static FileConfiguration getLanguage() {
        return LANGUAGE.get();
    }

}
