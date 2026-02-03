package org.togudv.monarquiArena;

import io.lumine.mythic.api.mobs.MobManager;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.togudv.monarquiArena.core.MythicService;

public final class MonarquiArena extends JavaPlugin {

    private MythicService mythicService;

    @Override
    public void onEnable() {
        this.mythicService = new MythicService();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}


