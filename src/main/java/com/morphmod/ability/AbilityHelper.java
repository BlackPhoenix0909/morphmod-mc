package com.morphmod.ability;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AbilityHelper {
    public static void applyEffect(ServerPlayerEntity player, StatusEffect effect, int durationTicks, int amplifier) {
        player.addStatusEffect(new StatusEffectInstance(effect, durationTicks, amplifier, false, true));
    }
    public static void msg(ServerPlayerEntity player, String text, Formatting color) {
        player.sendMessage(Text.literal(text).formatted(color), true);
    }
}
