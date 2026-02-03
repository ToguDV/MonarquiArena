package org.togudv.monarquiArena.core;

import org.bukkit.entity.Player;
import org.togudv.monarquiArena.model.Arena;

import java.util.List;

public class ArenaSession {
    private Arena arena;
    private int currentPlayers;
    private int currentRound;
    private List<Player> players;

    ArenaSession (Arena arena) {
        this.arena = arena;
    }

    public void AddPlayer(Player player) {
        players.add(player);
    }

    public void RemovePlayer(Player player) {
        players.remove(player);
    }

    private void SpawnMob(String name) {

    }


}
