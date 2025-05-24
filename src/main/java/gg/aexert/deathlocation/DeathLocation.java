package gg.aexert.deathlocation;

import gg.aexert.deathlocation.commands.DLCommandManager;
import gg.aexert.deathlocation.listeners.PlayerDeathListener;
import gg.aexert.deathlocation.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathLocation extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Console messages with standard colors
        getLogger().info(ChatColor.GOLD + "DeathLocation " + ChatColor.DARK_GRAY + "• " +
                ChatColor.WHITE + "Plugin by Aexert");
        getLogger().info(ChatColor.GOLD + "DeathLocation " + ChatColor.DARK_GRAY + "• " +
                ChatColor.WHITE + "Version 1.0.0");
        getLogger().info(ChatColor.GOLD + "DeathLocation " + ChatColor.DARK_GRAY + "• " +
                ChatColor.WHITE + "Loading configuration...");

        this.configManager = new ConfigManager(this);
        configManager.loadConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        new DLCommandManager(this);

        getLogger().info(ChatColor.GOLD + "DeathLocation " + ChatColor.DARK_GRAY + "• " +
                ChatColor.WHITE + "Plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GOLD + "DeathLocation " + ChatColor.DARK_GRAY + "• " +
                ChatColor.WHITE + "Plugin disabled");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}