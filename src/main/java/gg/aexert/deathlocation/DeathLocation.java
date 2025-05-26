package gg.aexert.deathlocation;

import gg.aexert.deathlocation.commands.DLCommandManager;
import gg.aexert.deathlocation.listeners.PlayerDeathListener;
import gg.aexert.deathlocation.utils.ConfigManager;
import gg.aexert.deathlocation.utils.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathLocation extends JavaPlugin {
    private ConfigManager configManager;
    private CooldownManager cooldownManager;

    // ANSI escape code for purple (light magenta)
    private static final String PURPLE = "\u001B[35m";
    private static final String RESET = "\u001B[0m";

    @Override
    public void onEnable() {
        getLogger().info(PURPLE + "DeathLocation • v1.1.0 by Aexert" + RESET);
        getLogger().info(PURPLE + "DeathLocation • Loading configuration..." + RESET);

        this.configManager = new ConfigManager(this);
        this.cooldownManager = new CooldownManager(this);
        configManager.loadConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        new DLCommandManager(this);

        getLogger().info(PURPLE + "DeathLocation • Plugin enabled successfully!" + RESET);
    }

    @Override
    public void onDisable() {
        getLogger().info(PURPLE + "DeathLocation • Plugin disabled" + RESET);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
