package com.morphmod.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Tracks morph state for all visible players (for rendering). */
public class MorphRenderState {
    // UUID -> morph entity ID string (null = human)
    private static final Map<UUID, String> playerMorphs = new HashMap<>();
    private static String selfMorph = null;

    public static void updatePlayer(UUID uuid, String morphId) {
        if (morphId == null || morphId.equals("player")) {
            playerMorphs.remove(uuid);
        } else {
            playerMorphs.put(uuid, morphId);
        }
    }

    public static void updateSelf(String morphId) {
        selfMorph = morphId;
    }

    public static String getMorph(UUID uuid) {
        return playerMorphs.get(uuid);
    }

    public static String getSelfMorph() {
        return selfMorph;
    }
}
