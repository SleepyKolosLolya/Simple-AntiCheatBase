package acname.ac.util;


import acname.ac.plugin.Global;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private final String fileName;
    private File file;
    private FileConfiguration configuration;

    public CustomConfig(String filename) {
        this.fileName = filename;
    }

    public void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin(Global.FOLDER_NAME).getDataFolder(), this.fileName);

        if (!file.exists()) {
            Global.plugin.saveResource(fileName, false);
        }
        setCustomFile(YamlConfiguration.loadConfiguration(file));
    }

    public FileConfiguration get() {
        return getCustomFile();
    }

    public void save() {
        try {
            configuration.save(getFile());
        } catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(getFile());
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCustomFile() {
        return configuration;
    }

    public void setCustomFile(FileConfiguration customFile) {
        this.configuration = customFile;
    }

}

