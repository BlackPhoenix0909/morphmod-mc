package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class VexAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.LEVITATION, 40, 1);
        AbilityHelper.applyEffect(p, StatusEffects.INVISIBILITY, 60, 0);
        AbilityHelper.applyEffect(p, StatusEffects.STRENGTH, 60, 2);
        AbilityHelper.msg(p, "👿 Vex Phase!", Formatting.DARK_PURPLE);
    }
}
