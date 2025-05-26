package gg.aexert.deathlocation.listeners;

import gg.aexert.deathlocation.DeathLocation;
import gg.aexert.deathlocation.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final DeathLocation plugin;

    public PlayerDeathListener(DeathLocation plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        plugin.getConfigManager().saveDeathLocation(player);

        if (plugin.getConfigManager().getConfig().getBoolean("players." + player.getUniqueId() + ".enabled", true)) {
            String dimension = getDimensionName(player.getWorld().getEnvironment());
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();

            String message = plugin.getConfigManager().getConfig().getString("death-message")
                    .replace("{Dimension}", dimension)
                    .replace("{X}", String.valueOf(x))
                    .replace("{Y}", String.valueOf(y))
                    .replace("{Z}", String.valueOf(z));

            player.sendMessage(ColorUtils.translateHexCodes(message));
        }
    }

    private String getDimensionName(org.bukkit.World.Environment env) {
        switch(env) {
            case NETHER: return "Nether";
            case THE_END: return "The End";
            default: return "Overworld";
        }
    }
}
