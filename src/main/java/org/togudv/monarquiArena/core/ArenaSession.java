package org.togudv.monarquiArena.core;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.togudv.monarquiArena.model.Arena;
import org.togudv.monarquiArena.model.ArenaWave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArenaSession {
    private Arena arena;
    private int currentPlayers;
    private int currentRound;
    private List<Player> players;
    private MythicService mythicService;
    private final Plugin plugin;
    private BukkitRunnable startTimer;
    private int countdownSeconds;
    private boolean isStarting;
    private boolean isRunning;

    ArenaSession (Arena arena, MythicService mythicService, Plugin plugin){
        this.arena = arena;
        this.mythicService = mythicService;
        this.plugin = plugin;
        this.players = new ArrayList<>();
        this.currentRound = 0;
        this.isStarting = false;
        this.isRunning = false;
    }

    public void AddPlayer(Player player) {
        players.add(player);
        checkStartConditions();
    }

    public void RemovePlayer(Player player) {
        players.remove(player);
        checkStartConditions();
    }

    private void SpawnMob(String name, Location location) {
        mythicService.SpawnByName(name, location);
    }

    private void NextRound() {
        if(arena.getRounds() <= currentRound) return;
        currentRound++;
    }

    public void spawnCurrentRoundMobs() {
        if (players.isEmpty() || currentRound == 0) return;
        
        List<ArenaWave> roundWaves = arena.getWavesByRound(currentRound);
        if (roundWaves.isEmpty()) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                List<ArenaWave> availableWaves = roundWaves.stream()
                    .filter(w -> w.getSpawned() < w.getAmount())
                    .toList();
                
                if (availableWaves.isEmpty()) {
                    this.cancel();
                    return;
                }
                Random random = new Random();
                ArenaWave selectedWave = availableWaves.get(random.nextInt(availableWaves.size()));
                spawnRandomMobFromWave(selectedWave);
            }
        }.runTaskTimer(plugin, 0L, ThreadLocalRandom.current().nextInt(2, 11)); // 0.1s - 0.5s (2-11 ticks)
    }

    private void spawnRandomMobFromWave(ArenaWave wave) {
        if (players.isEmpty() || wave.getSpawned() >= wave.getAmount()) return;
        Random random = new Random();
        Player randomPlayer = players.get(random.nextInt(players.size()));
        Location nearestSpawn = findNearestSpawnLocation(randomPlayer.getLocation());
        
        SpawnMob(wave.getMythicMobName(), nearestSpawn);
        wave.incrementSpawned();
    }

    private Location findNearestSpawnLocation(Location playerLocation) {
        return arena.getSpawnLocations().stream()
            .min((loc1, loc2) -> Double.compare(
                playerLocation.distance(loc1),
                playerLocation.distance(loc2)
            ))
            .orElse(arena.getSpawnLocations().get(0));
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= arena.getMaxPlayers();
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public Arena getArena() {
        return arena;
    }

    private void startCountdown(int seconds) {
        if (isRunning || isStarting) return;
        
        if (players.size() < arena.getMinPlayers()) return;

        cancelCountdown();
        
        this.countdownSeconds = seconds;
        this.isStarting = true;
        
        broadcastMessage("¡Arena " + arena.getId() + " iniciando en " + seconds + " segundos!");
        
        startTimer = (BukkitRunnable) new BukkitRunnable() {
            @Override
            public void run() {
                countdownSeconds--;
                
                if (countdownSeconds > 0) {
                    if (countdownSeconds % 10 == 0 || countdownSeconds <= 5) {
                        broadcastMessage("¡Arena " + arena.getId() + " iniciando en " + countdownSeconds + " segundos!");
                    }
                } else {
                    startArena();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    private void cancelCountdown() {
        if (startTimer != null) {
            startTimer.cancel();
            startTimer = null;
        }
        isStarting = false;
        countdownSeconds = 0;
    }

    private void startArena() {
        if (players.size() < arena.getMinPlayers()) {
            broadcastMessage("¡Arena " + arena.getId() + " cancelada - jugadores insuficientes!");
            isStarting = false;
            return;
        }
        
        isRunning = true;
        isStarting = false;
        broadcastMessage("¡Arena " + arena.getId() + " iniciada!");
        
        NextRound();
        spawnCurrentRoundMobs();
    }

    private void broadcastMessage(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    private void checkStartConditions() {
        if (isRunning) return;
        
        if (players.isEmpty()) {
            cancelCountdown();
            return;
        }
        
        if (players.size() < arena.getMinPlayers()) {
            if (isStarting) {
                broadcastMessage("¡Arena " + arena.getId() + " cancelada - jugadores insuficientes!");
                cancelCountdown();
            }
            return;
        }
        
        if (isFull()) {
            startCountdown(10);
        } else if (!isStarting) {
            startCountdown(60);
        }
    }

}
