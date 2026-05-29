package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;

/** Spider: can climb walls. Handled by mixin. */
public class SpiderAbility {
    public static void tick(ServerPlayerEntity player) {
        // Wall-climbing is handled via PlayerEntityMixin
    }
}
