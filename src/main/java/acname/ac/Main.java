package acname.ac;

import acname.ac.features.commands.ACCommandExecutor;
import acname.ac.util.config.CustomConfig;
import acname.ac.util.data.PluginUtils;
import acname.ac.util.events.ProtocolLibListener;
import acname.ac.util.tasks.ServerLag;
import acname.ac.util.tasks.TPS;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class Main extends JavaPlugin {

    public final Logger log = getLogger();
    private final String FOLDER_NAME = this.getDescription().getName();
    private final String PLUGIN_COMMAND = this.getDescription().getCommands().keySet().toArray()[0].toString();
    public ProtocolLibListener pll;

    @Override
    public void onEnable() {

        updateGlobalValues();

        Global.TPS_COUNTER.init();
        Global.SERVER_LAG.init();

        Global.PLUGIN.getServer().getPluginManager().registerEvents(new PluginUtils(), Global.PLUGIN);

        configSetup();

        pll = new ProtocolLibListener();

        getServer().getPluginCommand(PLUGIN_COMMAND).setExecutor(new ACCommandExecutor());
        this.getCommand(PLUGIN_COMMAND).setExecutor(new ACCommandExecutor());

        log.info("AC Base Enabled!");
    }

    @Override
    public void onDisable() {
        log.info("AC Base Disabled!");
    }

    private void updateGlobalValues() {

        Global.PLUGIN = this;
        Global.LOGGER = log;
        Global.MAIN_THREAD = Thread.currentThread();
        Global.TPS_COUNTER = new TPS();
        Global.SERVER_LAG = new ServerLag();
        Global.FOLDER_NAME = this.FOLDER_NAME;

    }

    private void configSetup() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Global.LANGUAGE = new CustomConfig("language.yml");
        Global.LANGUAGE.setup();

        Global.CONFIG = new CustomConfig("config.yml");
        Global.CONFIG.setup();

    }
}
