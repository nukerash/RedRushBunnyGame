package net.s0ckett.game;

import java.util.HashSet;
import java.util.Set;

public class PlayerListManager {
    private final Set<String> playerList = new HashSet<>();

    public void addPlayer(String playerName) {
        playerList.add(playerName);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public Set<String> getPlayerList() {
        return new HashSet<>(playerList);
    }

    public boolean containsPlayer(String playerName) {
        return playerList.contains(playerName);
    }
}