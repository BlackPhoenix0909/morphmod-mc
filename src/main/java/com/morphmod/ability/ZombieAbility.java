package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class ZombieAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.STRENGTH, 100, 1);
        AbilityHelper.applyEffect(p, StatusEffects.RESISTANCE, 100, 0);
        AbilityHelper.msg(p, "🧟 Zombie Rage!", Formatting.DARK_GREEN);
    }
}
