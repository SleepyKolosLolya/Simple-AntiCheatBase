package acname.ac.plugin;

import acname.ac.util.CustomConfig;
import acname.ac.util.tasks.ServerLag;
import acname.ac.util.tasks.TPS;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Global {
    public static ProtocolManager pm = ProtocolLibrary.getProtocolManager();
    public static Thread MAIN_THREAD;
    public static String FOLDER_NAME;
    public static TPS tpsCounter;
    public static ServerLag svlag;
    public static Plugin plugin;
    public static CustomConfig language;
    public static CustomConfig config;
    public static Logger logger;

    public static FileConfiguration getConfig() {
        return config.get();
    }

    public static FileConfiguration getLanguage() {
        return language.get();
    }
}
