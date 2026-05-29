package com.morphmod.ability;

import com.morphmod.MorphData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for mob-specific stats and abilities.
 * Each entry defines max health and attack damage for the morph,
 * plus a tick handler for special abilities.
 */
public class MobAbilityRegistry {

    public record MobStats(double maxHealth, double attackDamage, AbilityTicker abilityTicker) {}

    @FunctionalInterface
    public interface AbilityTicker {
        void tick(ServerPlayerEntity player);
    }

    private static final Map<EntityType<?>, MobStats> STATS = new HashMap<>();

    static {
        // === Passive / Neutral ===
        register(EntityType.COW,          10.0, 2.0, null);
        register(EntityType.PIG,          10.0, 2.0, null);
        register(EntityType.SHEEP,        8.0,  2.0, null);
        register(EntityType.CHICKEN,      4.0,  2.0, ChickenAbility::tick);
        register(EntityType.RABBIT,       3.0,  2.0, null);
        register(EntityType.HORSE,        30.0, 2.0, null);
        register(EntityType.WOLF,         8.0,  4.0, null);
        register(EntityType.CAT,          5.0,  2.0, null);
        register(EntityType.OCELOT,       5.0,  2.0, null);
        register(EntityType.FOX,          10.0, 2.0, null);
        register(EntityType.PANDA,        20.0, 6.0, null);
        register(EntityType.POLAR_BEAR,   30.0, 6.0, null);
        register(EntityType.LLAMA,        15.0, 1.0, null);
        register(EntityType.DOLPHIN,      10.0, 3.0, null);
        register(EntityType.SQUID,        10.0, 0.0, null);
        register(EntityType.TURTLE,       15.0, 2.0, null);
        register(EntityType.BEE,          10.0, 2.0, BeeAbility::tick);
        register(EntityType.MOOSHROOM,    10.0, 2.0, null);
        register(EntityType.GOAT,         10.0, 2.0, null);
        register(EntityType.FROG,         5.0,  1.0, null);
        register(EntityType.AXOLOTL,      7.0,  2.0, null);
        register(EntityType.ALLAY,        10.0, 2.0, null);
        register(EntityType.SNIFFER,      14.0, 5.0, null);
        register(EntityType.CAMEL,        32.0, 2.0, null);
        register(EntityType.ARMADILLO,    6.0,  2.0, null);

        // === Hostile ===
        register(EntityType.ZOMBIE,       20.0, 3.0, null);
        register(EntityType.SKELETON,     20.0, 2.0, SkeletonAbility::tick);
        register(EntityType.CREEPER,      20.0, 3.0, CreeperAbility::tick);
        register(EntityType.SPIDER,       16.0, 2.0, SpiderAbility::tick);
        register(EntityType.CAVE_SPIDER,  12.0, 2.0, SpiderAbility::tick);
        register(EntityType.ENDERMAN,     40.0, 7.0, EndermanAbility::tick);
        register(EntityType.BLAZE,        20.0, 6.0, BlazeAbility::tick);
        register(EntityType.GHAST,        10.0, 6.0, GhastAbility::tick);
        register(EntityType.WITCH,        26.0, 2.0, null);
        register(EntityType.SLIME,        16.0, 4.0, null);
        register(EntityType.MAGMA_CUBE,   16.0, 6.0, null);
        register(EntityType.SILVERFISH,   4.0,  1.0, null);
        register(EntityType.DROWNED,      20.0, 2.0, null);
        register(EntityType.HUSK,         20.0, 3.0, null);
        register(EntityType.STRAY,        20.0, 2.0, null);
        register(EntityType.PHANTOM,      20.0, 6.0, null);
        register(EntityType.PIGLIN,       16.0, 5.0, null);
        register(EntityType.HOGLIN,       40.0, 6.0, null);
        register(EntityType.ZOGLIN,       40.0, 6.0, null);
        register(EntityType.PIGLIN_BRUTE, 50.0, 9.5, null);
        register(EntityType.ZOMBIFIED_PIGLIN, 20.0, 5.0, null);
        register(EntityType.VINDICATOR,   24.0, 8.0, null);
        register(EntityType.PILLAGER,     24.0, 4.0, null);
        register(EntityType.RAVAGER,      100.0, 12.0, null);
        register(EntityType.VINNGER,      20.0, 6.0, null);
        register(EntityType.EVOKER,       24.0, 6.0, EvokerAbility::tick);
        register(EntityType.SHULKER,      30.0, 4.0, null);
        register(EntityType.GUARDIAN,     30.0, 6.0, null);
        register(EntityType.ELDER_GUARDIAN, 80.0, 8.0, null);
        register(EntityType.ENDERMITE,    4.0,  2.0, null);

        // === Bosses ===
        register(EntityType.WARDEN,       500.0, 30.0, WardenAbility::tick);
        register(EntityType.ENDER_DRAGON, 200.0, 15.0, null);
        register(EntityType.WITHER,       300.0, 8.0,  WitherAbility::tick);
    }

    private static void register(EntityType<?> type, double health, double damage, AbilityTicker ticker) {
        STATS.put(type, new MobStats(health, damage, ticker));
    }

    public static double getMaxHealth(EntityType<?> type) {
        MobStats stats = STATS.get(type);
        return stats != null ? stats.maxHealth() : 20.0;
    }

    public static double getAttackDamage(EntityType<?> type) {
        MobStats stats = STATS.get(type);
        return stats != null ? stats.attackDamage() : 1.0;
    }

    public static void tickAbilities(ServerPlayerEntity player) {
        MorphData.getActiveMorphType(player.getUuid()).ifPresent(type -> {
            MobStats stats = STATS.get(type);
            if (stats != null && stats.abilityTicker() != null) {
                stats.abilityTicker().tick(player);
            }
        });
    }
}
