package com.galygious.galysentials;

import java.util.*;

public class HomeManager {
    private final Map<UUID, Map<String, Home>> playerHomes = new HashMap<>();

    public Map<String, Home> getPlayerHomes(UUID playerId) {
        return playerHomes.getOrDefault(playerId, Collections.emptyMap());
    }

    public Home getHome(UUID playerId, String homeName) {
        Map<String, Home> homes = playerHomes.get(playerId);
        return homes != null ? homes.get(homeName) : null;
    }

    public void setHome(UUID playerId, String homeName, Home home) {
        playerHomes.computeIfAbsent(playerId, k -> new HashMap<>()).put(homeName, home);
    }

    public boolean deleteHome(UUID playerUUID, String homeName) {
        Map<String, Home> playerHomes = homes.get(playerUUID);
        if (playerHomes == null || !playerHomes.containsKey(homeName)) {
            return false;
        }

        playerHomes.remove(homeName);
        saveHomes();
        return true;
    }


    public Collection<Home> getHomes(UUID playerUUID) {
        return playerHomes.getOrDefault(playerUUID, new HashMap<>()).values();
    }
}
