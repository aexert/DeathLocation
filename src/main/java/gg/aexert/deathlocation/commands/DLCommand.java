package gg.aexert.deathlocation.commands;

import gg.aexert.deathlocation.DeathLocation;
import gg.aexert.deathlocation.utils.ColorUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DLCommand implements CommandExecutor {
    private final DeathLocation plugin;

    public DLCommand(DeathLocation plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;
            case "toggle":
                handleToggle(sender);
                break;
            case "info":
                handleInfo(sender);
                break;
            case "show":
                handleShow(sender);
                break;
            default:
                sendHelp(sender);
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("deathlocation.reload")) {
            sender.sendMessage(ColorUtils.translateHexCodes("&cNo permission!"));
            return;
        }
        plugin.getConfigManager().reloadConfig();
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>Config reloaded!"));
    }

    private void handleToggle(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.translateHexCodes("&cPlayers only!"));
            return;
        }

        if (plugin.getCooldownManager().isOnCooldown(player)) {
            player.sendMessage(ColorUtils.translateHexCodes(
                    "<#FF0000>Wait " + plugin.getCooldownManager().getRemainingCooldown(player) + "s before using this again!"
            ));
            return;
        }

        boolean current = plugin.getConfigManager().getConfig()
                .getBoolean("players." + player.getUniqueId() + ".enabled", true);
        boolean newValue = !current;
        plugin.getConfigManager().getConfig()
                .set("players." + player.getUniqueId() + ".enabled", newValue);
        plugin.getConfigManager().saveConfig();
        player.sendMessage(ColorUtils.translateHexCodes(
                "<#8A5FFF>Death messages " + (newValue ? "<#00FF00>enabled" : "<#FF0000>disabled")));
    }

    private void handleInfo(CommandSender sender) {
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>DeathLocation v1.1.0"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>Author: Aexert"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>GitHub: https://github.com/aexert"));
    }

    private void handleShow(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.translateHexCodes("&cPlayers only!"));
            return;
        }

        if (plugin.getCooldownManager().isOnCooldown(player)) {
            player.sendMessage(ColorUtils.translateHexCodes(
                    "<#FF0000>Wait " + plugin.getCooldownManager().getRemainingCooldown(player) + "s before using this again!"
            ));
            return;
        }

        Location deathLoc = plugin.getConfigManager().getDeathLocation(player);
        if (deathLoc == null) {
            player.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>No death location recorded"));
            return;
        }

        String dimension = switch(deathLoc.getWorld().getEnvironment()) {
            case NETHER -> "Nether";
            case THE_END -> "The End";
            default -> "Overworld";
        };

        String message = plugin.getConfigManager().getConfig().getString("death-message")
                .replace("{Dimension}", dimension)
                .replace("{X}", String.valueOf(deathLoc.getBlockX()))
                .replace("{Y}", String.valueOf(deathLoc.getBlockY()))
                .replace("{Z}", String.valueOf(deathLoc.getBlockZ()));

        player.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>Your last death location:"));
        player.sendMessage(ColorUtils.translateHexCodes(message));
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>DeathLocation Help:"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>/dl reload - Reload config"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>/dl toggle - Toggle messages"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>/dl info - Plugin info"));
        sender.sendMessage(ColorUtils.translateHexCodes("<#8A5FFF>/dl show - Show death location"));
    }
}