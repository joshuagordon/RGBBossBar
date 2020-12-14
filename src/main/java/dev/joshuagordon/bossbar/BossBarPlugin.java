package dev.joshuagordon.bossbar;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BossBarPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // Config
        BossBarListener ACBossBar = new BossBarListener(this);
        ACBossBar.setupConfig();
        ACBossBar.createBossBar();

        // Register Event Listener/s
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(ACBossBar, this);
    }
}