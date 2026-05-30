package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class SquidAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.BLINDNESS, 60, 0);
        AbilityHelper.applyEffect(p, StatusEffects.INVISIBILITY, 100, 0);
        AbilityHelper.msg(p, "🦑 Ink Cloud! Blind nearby + Invisible!", Formatting.DARK_GRAY);
    }
}
