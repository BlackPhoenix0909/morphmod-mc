package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;

public class SkeletonAbility {
    public static void tick(ServerPlayerEntity player) {
        if (player.getServerWorld().isDay()
                && !player.getServerWorld().isRaining()
                && player.getServerWorld().isSkyVisible(player.getBlockPos())
                && player.isAlive()) {
            player.setOnFireFor(1);
        }
    }
}
