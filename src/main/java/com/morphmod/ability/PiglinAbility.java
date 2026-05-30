package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class PiglinAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.FIRE_RESISTANCE, 400, 0);
        AbilityHelper.applyEffect(p, StatusEffects.STRENGTH, 100, 0);
        AbilityHelper.msg(p, "🐷 Nether Fury!", Formatting.GOLD);
    }
}
