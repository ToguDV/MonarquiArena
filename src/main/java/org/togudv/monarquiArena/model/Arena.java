package org.togudv.monarquiArena.model;

import org.bukkit.Location;
import org.togudv.monarquiArena.core.ArenaSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Arena {
    private final String id;
    boolean isEnabled;
    private int maxPlayers;
    private int minPlayers;
    private final int maxTime;
    private final List<ArenaWave> waves;
    private final List<Location> spawnLocations;
    private final int rounds;


    Arena(String id, int minPlayers, int maxPlayers, List<ArenaWave> waves, int maxTime, List<Location> spawnLocations, int rounds) {
        this.maxTime = maxTime;
        this.id = id;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.waves = waves;
        this.isEnabled = false;
        this.spawnLocations = spawnLocations;
        this.rounds = waves.getLast().getRoundNumber();
    }

    public int getRounds() {
        return rounds;
    }

    public List<ArenaWave> getWavesByRound(int round) {
        return waves.stream().filter(w -> w.getRoundNumber() == round && w.getSpawned() == 0).toList();
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return  isEnabled;
    }

}
