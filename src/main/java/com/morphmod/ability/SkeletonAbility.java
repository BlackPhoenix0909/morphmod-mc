package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;

/** Skeleton: burns in sunlight. */
public class SkeletonAbility {
    public static void tick(ServerPlayerEntity player) {
        if (player.getServerWorld().isDay() && !player.getServerWorld().isRaining()) {
            if (!player.isInShadow() && player.isAlive()) {
                player.setOnFireFor(1);
            }
        }
    }
}
