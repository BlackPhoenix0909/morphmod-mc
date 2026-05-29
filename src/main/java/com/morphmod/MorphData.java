package com.morphmod;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import java.util.*;

/**
 * Holds per-player morph data: which mobs they have unlocked and their current morph.
 * This is stored server-side and synced to the client.
 */
public class MorphData {

    // Maps player UUID -> set of unlocked entity type IDs
    private static final Map<UUID, Set<String>> unlockedMorphs = new HashMap<>();
    // Maps player UUID -> current morph entity type ID (null = human)
    private static final Map<UUID, String> activeMorphs = new HashMap<>();

    public static void unlockMorph(UUID playerId, EntityType<?> entityType) {
        String id = Registries.ENTITY_TYPE.getId(entityType).toString();
        unlockedMorphs.computeIfAbsent(playerId, k -> new HashSet<>()).add(id);
    }

    public static boolean hasMorph(UUID playerId, EntityType<?> entityType) {
        String id = Registries.ENTITY_TYPE.getId(entityType).toString();
        Set<String> unlocked = unlockedMorphs.getOrDefault(playerId, Collections.emptySet());
        return unlocked.contains(id);
    }

    public static Set<String> getUnlockedMorphs(UUID playerId) {
        return Collections.unmodifiableSet(
            unlockedMorphs.getOrDefault(playerId, Collections.emptySet())
        );
    }

    public static void setActiveMorph(UUID playerId, String morphId) {
        if (morphId == null || morphId.equals("player")) {
            activeMorphs.remove(playerId);
        } else {
            activeMorphs.put(playerId, morphId);
        }
    }

    public static String getActiveMorph(UUID playerId) {
        return activeMorphs.get(playerId); // null = human
    }

    public static boolean isMorphed(UUID playerId) {
        return activeMorphs.containsKey(playerId);
    }

    /** Get the EntityType for a player's active morph, or null if human */
    public static Optional<EntityType<?>> getActiveMorphType(UUID playerId) {
        String id = activeMorphs.get(playerId);
        if (id == null) return Optional.empty();
        return Registries.ENTITY_TYPE.getOrEmpty(new Identifier(id));
    }
}
