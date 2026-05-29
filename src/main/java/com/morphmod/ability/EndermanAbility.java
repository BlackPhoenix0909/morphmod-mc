package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;

/** Enderman: takes damage from water. Handled via mixin. */
public class EndermanAbility {
    public static void tick(ServerPlayerEntity player) {
        if (player.isTouchingWater()) {
            player.damage(player.getServerWorld().getDamageSources().drown(), 1.0f);
        }
    }
}
