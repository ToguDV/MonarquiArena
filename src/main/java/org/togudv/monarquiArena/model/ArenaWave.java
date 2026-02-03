package org.togudv.monarquiArena.model;

public class ArenaWave {
    private final int roundNumber;
    private final String mythicMobName;
    private final int amount;
    private int spawned = 0;

    public ArenaWave(int waveNumber, String mythicMobName, int amount) {
        this.roundNumber = waveNumber;
        this.mythicMobName = mythicMobName;
        this.amount = amount;
    }

    public int getRoundNumber() { return roundNumber; }
    public String getMythicMobName() { return mythicMobName; }
    public int getAmount() { return amount; }
    public int getSpawned() { return spawned; }
    public void incrementSpawned() { this.spawned++; }
}
