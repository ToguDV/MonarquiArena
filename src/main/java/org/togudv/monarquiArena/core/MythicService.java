package org.togudv.monarquiArena.core;

import io.lumine.mythic.api.mobs.MobManager;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class MythicService {
    private MythicBukkit mythicBukkit;
    private MobManager mobManager;

    public MythicService() {
        mythicBukkit = MythicBukkit.inst();
        mobManager = mythicBukkit.getMobManager();
    }

    public Entity SpawnByName (String name, Location location, int level) {
        MythicMob mob = mobManager.getMythicMob(name).orElse(null);
        if(mob != null) {
            ActiveMob activeMob = mob.spawn(BukkitAdapter.adapt(location), level);
            return activeMob.getEntity().getBukkitEntity();
        }
        return null;
    }

    //Sobrecarga de SpawnByName con nivel por defecto 1
    public Entity SpawnByName (String name, Location location) {
        return SpawnByName(name, location, 1);
    }

}
