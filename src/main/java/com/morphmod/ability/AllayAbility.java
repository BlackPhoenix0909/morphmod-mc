package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class AllayAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.LUCK, 300, 2);
        AbilityHelper.applyEffect(p, StatusEffects.SPEED, 100, 1);
        AbilityHelper.msg(p, "🧚 Allay Dance! Luck!", Formatting.LIGHT_PURPLE);
    }
}
