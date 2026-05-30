package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class DolphinAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.DOLPHINS_GRACE, 200, 0);
        AbilityHelper.applyEffect(p, StatusEffects.WATER_BREATHING, 200, 0);
        AbilityHelper.msg(p, "🐬 Dolphin Grace + Water Breathing!", Formatting.AQUA);
    }
}
