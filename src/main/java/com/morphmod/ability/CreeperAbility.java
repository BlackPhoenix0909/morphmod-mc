package com.morphmod.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

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
            world.createExplosion(
                player,
                null,
                null,
                player.getX(), player.getY(), player.getZ(),
                3.0f,
                false,
                World.ExplosionSourceType.MOB
            );
            cooldowns.put(id, COOLDOWN);
        }
    }
}
