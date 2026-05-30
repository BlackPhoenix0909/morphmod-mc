package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
public class FrogAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.JUMP_BOOST, 40, 4);
        AbilityHelper.msg(p, "🐸 Super Leap!", Formatting.GREEN);
    }
}
