package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class PhantomAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.LEVITATION, 60, 2);
        AbilityHelper.applyEffect(p, StatusEffects.INVISIBILITY, 100, 0);
        AbilityHelper.msg(p, "👻 Phantom Dive!", Formatting.DARK_GRAY);
    }
}
