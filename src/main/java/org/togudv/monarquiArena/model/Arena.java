package org.togudv.monarquiArena.model;

import org.bukkit.Location;
import org.togudv.monarquiArena.core.ArenaSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {
    private final String id;
    boolean isEnabled;
    private int maxPlayers;
    private int minPlayers;
    private final int maxTime;
    private final List<ArenaWave> waves;
    private final List<Location> spawnLocations;


    Arena(String id, int minPlayers, int maxPlayers, List<ArenaWave> waves, int maxTime, List<Location> spawnLocations) {
        this.maxTime = maxTime;
        this.id = id;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.waves = waves;
        this.isEnabled = false;
        this.spawnLocations = spawnLocations;

    }

}
