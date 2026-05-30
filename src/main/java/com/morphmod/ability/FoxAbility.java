package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class FoxAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.JUMP_BOOST, 60, 3);
        AbilityHelper.msg(p, "🦊 Fox Leap!", Formatting.GOLD);
    }
}
