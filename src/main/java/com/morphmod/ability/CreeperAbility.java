package com.morphmod.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreeperAbility {
    private static final Map<UUID, Integer> cooldowns = new HashMap<>();
    private static final float TRIGGER_RANGE = 2.5f;
    private static final int COOLDOWN = 200;

    public static void tick(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int cd = cooldowns.getOrDefault(id, 0);
        if (cd > 0) { cooldowns.put(id, cd - 1); return; }

        ServerWorld world = player.getServerWorld();
        List<LivingEntity> near = world.getEntitiesByClass(LivingEntity.class,
            player.getBoundingBox().expand(TRIGGER_RANGE),
            e -> e != player && !(e instanceof ServerPlayerEntity));

        if (!near.isEmpty()) {
            // 1.20.4 signature: createExplosion(Entity, DamageSource, ExplosionBehavior, Vec3d, float, boolean, ExplosionSourceType)
            // Simplest working call: pass null for DamageSource+Behavior to use defaults
            world.createExplosion(
                player,
                null,
                null,
                player.getPos(),
                3.0f,
                false,
                net.minecraft.world.explosion.ExplosionSourceType.MOB
            );
            cooldowns.put(id, COOLDOWN);
        }
    }
}
