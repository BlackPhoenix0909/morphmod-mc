package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class WitchAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.SPEED, 100, 1);
        AbilityHelper.applyEffect(p, StatusEffects.FIRE_RESISTANCE, 200, 0);
        AbilityHelper.applyEffect(p, StatusEffects.WATER_BREATHING, 200, 0);
        AbilityHelper.msg(p, "🧙‍♀️ Witch Brew!", Formatting.DARK_PURPLE);
    }
}
