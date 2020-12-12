package acname.ac.plugin;

import acname.ac.features.commands.ACCommandExecutor;
import acname.ac.util.CustomConfig;
import acname.ac.util.PluginUtils;
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

        Global.tpsCounter.init();
        Global.svlag.init();

        Global.plugin.getServer().getPluginManager().registerEvents(new PluginUtils(), Global.plugin);

        configSetup();

        pll = new ProtocolLibListener();

        requireNonNull(getServer().getPluginCommand(PLUGIN_COMMAND)).setExecutor(new ACCommandExecutor());
        requireNonNull(this.getCommand(PLUGIN_COMMAND)).setExecutor(new ACCommandExecutor());

        log.info("AC Base Enabled!");
    }

    @Override
    public void onDisable() {
        log.info("AC Base Disabled!");
    }

    private void updateGlobalValues() {

        Global.plugin = this;
        Global.logger = log;
        Global.MAIN_THREAD = Thread.currentThread();
        Global.tpsCounter = new TPS();
        Global.svlag = new ServerLag();
        Global.FOLDER_NAME = this.FOLDER_NAME;


    }

    private void configSetup() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Global.language = new CustomConfig("language.yml");
        Global.language.setup();

        Global.config = new CustomConfig("config.yml");
        Global.config.setup();

    }
}
