package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class SilverfishAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.SPEED, 60, 3);
        AbilityHelper.applyEffect(p, StatusEffects.HASTE, 60, 2);
        AbilityHelper.msg(p, "🐛 Silverfish Frenzy!", Formatting.GRAY);
    }
}
