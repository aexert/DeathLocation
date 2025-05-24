package gg.aexert.deathlocation.utils;

import gg.aexert.deathlocation.DeathLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final DeathLocation plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(DeathLocation plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config: " + e.getMessage());
        }
    }

    public void saveDeathLocation(Player player) {
        Location loc = player.getLocation();
        getConfig().set("death-locations." + player.getUniqueId() + ".world", loc.getWorld().getName());
        getConfig().set("death-locations." + player.getUniqueId() + ".x", loc.getX());
        getConfig().set("death-locations." + player.getUniqueId() + ".y", loc.getY());
        getConfig().set("death-locations." + player.getUniqueId() + ".z", loc.getZ());
        saveConfig();
    }

    public Location getDeathLocation(Player player) {
        ConfigurationSection section = getConfig().getConfigurationSection("death-locations." + player.getUniqueId());
        if (section == null) return null;

        World world = Bukkit.getWorld(section.getString("world"));
        if (world == null) return null;

        return new Location(
                world,
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z")
        );
    }
}