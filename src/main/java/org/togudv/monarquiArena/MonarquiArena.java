package org.togudv.monarquiArena;

import io.lumine.mythic.api.mobs.MobManager;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.togudv.monarquiArena.commands.ArenaCommand;
import org.togudv.monarquiArena.core.ArenaManager;
import org.togudv.monarquiArena.core.MythicService;

public final class MonarquiArena extends JavaPlugin {

    private MythicService mythicService;
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        this.mythicService = new MythicService();
        this.arenaManager = new ArenaManager(mythicService, this);
        
        getCommand("arena").setExecutor(new ArenaCommand(arenaManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}


