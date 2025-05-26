package gg.aexert.deathlocation.utils;

import gg.aexert.deathlocation.DeathLocation;
import org.bukkit.entity.Player;

import java.util.*;

public class CooldownManager {
    private final DeathLocation plugin;
    private final Map<UUID, Long> cooldowns = new WeakHashMap<>();

    public CooldownManager(DeathLocation plugin) {
        this.plugin = plugin;
    }

    public boolean isOnCooldown(Player player) {
        if (player.hasPermission("deathlocation.bypass.cooldown")) return false;

        long now = System.currentTimeMillis();
        Long lastUsed = cooldowns.get(player.getUniqueId());
        int cooldownTime = plugin.getConfigManager().getConfig().getInt("cooldown.seconds", 3) * 1000;

        if (lastUsed == null || now - lastUsed > cooldownTime) {
            cooldowns.put(player.getUniqueId(), now);
            return false;
        }
        return true;
    }

    public int getRemainingCooldown(Player player) {
        Long lastUsed = cooldowns.get(player.getUniqueId());
        if (lastUsed == null) return 0;

        int cooldownTime = plugin.getConfigManager().getConfig().getInt("cooldown.seconds", 3) * 1000;
        return (int) ((lastUsed + cooldownTime - System.currentTimeMillis()) / 1000);
    }
}
