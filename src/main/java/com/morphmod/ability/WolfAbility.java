package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class WolfAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.SPEED, 100, 2);
        AbilityHelper.applyEffect(p, StatusEffects.STRENGTH, 100, 1);
        AbilityHelper.msg(p, "🐺 Wolf Howl! Speed & Strength!", Formatting.GRAY);
    }
}
