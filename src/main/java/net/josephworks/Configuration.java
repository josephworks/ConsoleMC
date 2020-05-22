package net.josephworks;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private final ConsoleMC plugin;
    private final File configFile;
    private YamlConfiguration config;

    //Options
    private boolean op, permissions;

    public Configuration(ConsoleMC instance) {
        plugin = instance;
        configFile = new File(plugin.getDataFolder() + File.separator + "Configuration.yml");
        config = new YamlConfiguration();
    }

    public void loadConfig() {
        if (!configFile.exists()) createNewConfig();
        //Loading config
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        //Loading options
        ConfigurationSection mainSection = getConfigurationSectionParm();
        permissions = getBooleanParm(mainSection, "use-permissions", true);
        op = getBooleanParm(mainSection, "op-only", true);

        saveConfig();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void createNewConfig() {
        plugin.printMessage("Creating new config.");
        //Creating directories
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        //Creating file
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new YamlConfiguration();

        //Creating header
        config.options().header("Config:\n" +
                "use-permissions: If true, Bukkit permissions will be used.\n" +
                "op-only: If true, only ops are able to use the plugin.\n");
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        plugin.printMessage("Config saved to file.");
    }

    public void reloadConfig() {
        loadConfig();
    }

    ConfigurationSection getConfigurationSectionParm() {
        if (config.isConfigurationSection("Config")) return config.getConfigurationSection("Config");
        else return config.createSection("Config");
    }

    public Boolean getBooleanParm(ConfigurationSection section, String path, boolean def) {
        //If the value wasn't set already, create the property
        if (!section.contains(path)) section.set(path, def);

        //Return the actual value
        return section.getBoolean(path);
    }

    //Getters and Setters

    public boolean isPermissions() {
        return permissions;
    }

    public void setPermissions(boolean permissions) {
        this.permissions = permissions;
    }

    public boolean isOp() {
        return op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }
}