package com.morphmod.ability;

import com.morphmod.MorphData;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class MobAbilityRegistry {

    public record MobStats(double maxHealth, double attackDamage, AbilityHandler abilityHandler) {}

    @FunctionalInterface
    public interface AbilityHandler {
        void trigger(ServerPlayerEntity player);
    }

    private static final Map<EntityType<?>, MobStats> STATS = new HashMap<>();

    static {
        // Passive / Neutral
        register(EntityType.COW,            10.0, 2.0,  null);
        register(EntityType.PIG,            10.0, 2.0,  null);
        register(EntityType.SHEEP,           8.0, 2.0,  null);
        register(EntityType.CHICKEN,         4.0, 2.0,  ChickenAbility::trigger);
        register(EntityType.RABBIT,          3.0, 2.0,  null);
        register(EntityType.HORSE,          30.0, 2.0,  null);
        register(EntityType.WOLF,            8.0, 4.0,  WolfAbility::trigger);
        register(EntityType.CAT,             5.0, 2.0,  null);
        register(EntityType.OCELOT,          5.0, 2.0,  null);
        register(EntityType.FOX,            10.0, 2.0,  FoxAbility::trigger);
        register(EntityType.PANDA,          20.0, 6.0,  null);
        register(EntityType.POLAR_BEAR,     30.0, 6.0,  PolarBearAbility::trigger);
        register(EntityType.LLAMA,          15.0, 1.0,  LlamaAbility::trigger);
        register(EntityType.DOLPHIN,        10.0, 3.0,  DolphinAbility::trigger);
        register(EntityType.SQUID,          10.0, 0.0,  SquidAbility::trigger);
        register(EntityType.TURTLE,         15.0, 2.0,  null);
        register(EntityType.BEE,            10.0, 2.0,  BeeAbility::trigger);
        register(EntityType.MOOSHROOM,      10.0, 2.0,  null);
        register(EntityType.GOAT,           10.0, 2.0,  GoatAbility::trigger);
        register(EntityType.FROG,            5.0, 1.0,  FrogAbility::trigger);
        register(EntityType.AXOLOTL,         7.0, 2.0,  AxolotlAbility::trigger);
        register(EntityType.ALLAY,          10.0, 2.0,  AllayAbility::trigger);
        register(EntityType.SNIFFER,        14.0, 5.0,  null);
        register(EntityType.CAMEL,          32.0, 2.0,  null);

        // Hostile
        register(EntityType.ZOMBIE,         20.0, 3.0,  ZombieAbility::trigger);
        register(EntityType.SKELETON,       20.0, 2.0,  SkeletonAbility::trigger);
        register(EntityType.CREEPER,        20.0, 3.0,  CreeperAbility::trigger);
        register(EntityType.SPIDER,         16.0, 2.0,  SpiderAbility::trigger);
        register(EntityType.CAVE_SPIDER,    12.0, 2.0,  CaveSpiderAbility::trigger);
        register(EntityType.ENDERMAN,       40.0, 7.0,  EndermanAbility::trigger);
        register(EntityType.BLAZE,          20.0, 6.0,  BlazeAbility::trigger);
        register(EntityType.GHAST,          10.0, 6.0,  GhastAbility::trigger);
        register(EntityType.WITCH,          26.0, 2.0,  WitchAbility::trigger);
        register(EntityType.SLIME,          16.0, 4.0,  SlimeAbility::trigger);
        register(EntityType.MAGMA_CUBE,     16.0, 6.0,  MagmaCubeAbility::trigger);
        register(EntityType.SILVERFISH,      4.0, 1.0,  SilverfishAbility::trigger);
        register(EntityType.DROWNED,        20.0, 2.0,  DrownedAbility::trigger);
        register(EntityType.HUSK,           20.0, 3.0,  HuskAbility::trigger);
        register(EntityType.STRAY,          20.0, 2.0,  StrayAbility::trigger);
        register(EntityType.PHANTOM,        20.0, 6.0,  PhantomAbility::trigger);
        register(EntityType.PIGLIN,         16.0, 5.0,  PiglinAbility::trigger);
        register(EntityType.HOGLIN,         40.0, 6.0,  null);
        register(EntityType.ZOGLIN,         40.0, 6.0,  null);
        register(EntityType.PIGLIN_BRUTE,   50.0, 9.5,  null);
        register(EntityType.ZOMBIFIED_PIGLIN, 20.0, 5.0, null);
        register(EntityType.VINDICATOR,     24.0, 8.0,  null);
        register(EntityType.PILLAGER,       24.0, 4.0,  PillagerAbility::trigger);
        register(EntityType.RAVAGER,       100.0, 12.0,  RavagerAbility::trigger);
        register(EntityType.EVOKER,         24.0, 6.0,  EvokerAbility::trigger);
        register(EntityType.SHULKER,        30.0, 4.0,  ShulkerAbility::trigger);
        register(EntityType.GUARDIAN,       30.0, 6.0,  GuardianAbility::trigger);
        register(EntityType.ELDER_GUARDIAN, 80.0, 8.0,  ElderGuardianAbility::trigger);
        register(EntityType.ENDERMITE,       4.0, 2.0,  null);
        register(EntityType.IRON_GOLEM,    100.0, 15.0,  IronGolemAbility::trigger);
        register(EntityType.SNOW_GOLEM,      4.0,  2.0,  SnowGolemAbility::trigger);
        register(EntityType.VEX,            14.0,  9.0,  VexAbility::trigger);

        // Bosses
        register(EntityType.WARDEN,        500.0, 30.0,  WardenAbility::trigger);
        register(EntityType.ENDER_DRAGON,  200.0, 15.0,  EnderDragonAbility::trigger);
        register(EntityType.WITHER,        300.0,  8.0,  WitherAbility::trigger);
    }

    private static void register(EntityType<?> type, double health, double damage, AbilityHandler handler) {
        STATS.put(type, new MobStats(health, damage, handler));
    }

    public static double getMaxHealth(EntityType<?> type) {
        MobStats s = STATS.get(type);
        return s != null ? s.maxHealth() : 20.0;
    }

    public static double getAttackDamage(EntityType<?> type) {
        MobStats s = STATS.get(type);
        return s != null ? s.attackDamage() : 1.0;
    }

    public static void triggerAbility(ServerPlayerEntity player, EntityType<?> type) {
        MobStats stats = STATS.get(type);
        if (stats != null && stats.abilityHandler() != null) {
            stats.abilityHandler().trigger(player);
        }
    }
}
