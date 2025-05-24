package gg.aexert.deathlocation.commands;

import gg.aexert.deathlocation.DeathLocation;
import org.bukkit.command.PluginCommand;

public class DLCommandManager {
    public DLCommandManager(DeathLocation plugin) {
        PluginCommand command = plugin.getCommand("dl");
        if (command != null) {
            command.setExecutor(new DLCommand(plugin));
            command.setTabCompleter(new DLTabCompleter());
        }
    }
}