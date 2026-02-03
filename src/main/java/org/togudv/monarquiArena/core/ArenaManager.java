package org.togudv.monarquiArena.core;

import org.bukkit.entity.Player;
import org.togudv.monarquiArena.model.Arena;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {
    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<String, ArenaSession> activeSessions = new HashMap<>();

    public void StartSession(String arenaId) {
        ArenaSession arenaSession = new ArenaSession(arenas.get(arenaId));
        activeSessions.put(arenaId, arenaSession);
    }

}