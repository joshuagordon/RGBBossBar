package dev.joshuagordon.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BossBarListener implements Listener {

    private final BossBarPlugin plugin;
    private final BukkitAudiences audience;
    private BossBar bossBar;

    public BossBarListener(BossBarPlugin instance) {
        plugin = instance;
        audience = BukkitAudiences.create(plugin);
    }

    public void createBossBar() {
        Component title = MiniMessage.get().parse(plugin.getConfig().getString("title"));
        String barColorStr = plugin.getConfig().getString("barColor");
        String barStyleStr = plugin.getConfig().getString("barStyle");
        float progress = (float) plugin.getConfig().getDouble("progress");

        // Convert string to BarColor & BarStyle
        BossBar.Color color = BossBar.Color.valueOf(barColorStr);
        BossBar.Overlay overlay = BossBar.Overlay.valueOf(barStyleStr);

        bossBar = BossBar.bossBar(title, progress, color, overlay);
    }

    public void setupConfig() {
        // Create strings
        String world = "world";
        String title = "<rainbow>Camm is cool.</rainbow>"; // Yay for MiniMessage syntax
        String barColor = "PURPLE";
        String barStyle = "PROGRESS";
        float progress = 1f;

        // Add default configs
        plugin.getConfig().addDefault("world", world);
        plugin.getConfig().addDefault("title", title);
        plugin.getConfig().addDefault("barColor", barColor);
        plugin.getConfig().addDefault("barStyle", barStyle);
        plugin.getConfig().addDefault("progress", progress);

        // Copy Defaults
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    // Add bossbar to players as they join, if they're in the correct world
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String world = plugin.getConfig().getString("world");
        Player player = event.getPlayer();

        // Add bossbar to player who joined if their world is "world"
        if (player.getWorld().getName().equals(world)) {
            audience.player(player).showBossBar(bossBar);
        }
    }

    // Check if player changes world, remove boss bar if so
    @EventHandler
    public void onSwitchWorld(PlayerChangedWorldEvent event) {
        String world = plugin.getConfig().getString("world");
        Player player = event.getPlayer();

        // Add bossbar to player who joined if their world is "world"
        if (player.getWorld().getName().equals(world)) {
            audience.player(player).showBossBar(bossBar);
        }
        else {
            audience.player(player).hideBossBar(bossBar);
        }
    }
}