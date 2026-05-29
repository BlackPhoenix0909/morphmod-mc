package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;

/** Blaze: immune to fire, shoots fire charges periodically. */
public class BlazeAbility {
    public static void tick(ServerPlayerEntity player) {
        player.setFireTicks(0); // Immune to fire
    }
}
