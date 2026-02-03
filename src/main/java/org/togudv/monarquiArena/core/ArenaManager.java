package org.togudv.monarquiArena.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.togudv.monarquiArena.model.Arena;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {
    private Map<String, Arena> arenas = new HashMap<>();
    private Map<String, ArenaSession> activeSessions = new HashMap<>();
    private MythicService mythicService;
    private final Plugin plugin;

    public ArenaManager (MythicService mythicService, Plugin plugin) {
        this.mythicService = mythicService;
        this.plugin = plugin;
    }

public void StartSession(String arenaId) {
        ArenaSession arenaSession = new ArenaSession(arenas.get(arenaId), mythicService, plugin);
        activeSessions.put(arenaId, arenaSession);
    }

    public String joinArena(Player player, String arenaId) {
        if (!arenas.containsKey(arenaId)) {
            return "La arena '" + arenaId + "' no existe.";
        }

        Arena arena = arenas.get(arenaId);
        if (!arena.isEnabled()) {
            return "La arena '" + arenaId + "' no está habilitada.";
        }

        if (isPlayerInAnySession(player)) {
            return "Ya estás en una arena activa.";
        }

        ArenaSession session = getArenaSession(arenaId);
        if (session == null) {
            StartSession(arenaId);
            session = getArenaSession(arenaId);
        }

        if (session.isFull()) {
            return "La arena '" + arenaId + "' está llena.";
        }

        if (session.containsPlayer(player)) {
            return "Ya estás en esta arena.";
        }

        session.AddPlayer(player);
        return "Te has unido a la arena '" + arenaId + "'.";
    }

    public String leaveArena(Player player) {
        ArenaSession playerSession = getPlayerSession(player);
        if (playerSession == null) {
            return "No estás en ninguna arena activa.";
        }

        playerSession.RemovePlayer(player);
        return "Has salido de la arena.";
    }

    private boolean isPlayerInAnySession(Player player) {
        for (ArenaSession session : activeSessions.values()) {
            if (session.containsPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    private ArenaSession getPlayerSession(Player player) {
        for (ArenaSession session : activeSessions.values()) {
            if (session.containsPlayer(player)) {
                return session;
            }
        }
        return null;
    }

    public ArenaSession getArenaSession(String arenaId) {
        return activeSessions.get(arenaId);
    }

    public void addArena(Arena arena) {
        arenas.put(arena.getId(), arena);
    }

}