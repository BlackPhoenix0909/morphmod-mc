package com.morphmod.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;

import java.util.List;

public class ShulkerAbility {
    public static void trigger(ServerPlayerEntity p) {
        // Levitation auf alle Gegner in der Nähe (wie Shulker-Bullet Effekt)
        Box box = p.getBoundingBox().expand(8.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(
            LivingEntity.class, box, e -> e != p
        );
        for (LivingEntity e : targets) {
            e.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 1));
        }
        AbilityHelper.applyEffect(p, StatusEffects.RESISTANCE, 60, 1);
        AbilityHelper.msg(p, "📦 Shulker Levitation!", Formatting.LIGHT_PURPLE);
    }
}
