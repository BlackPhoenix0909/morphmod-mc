package com.morphmod.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;

import java.util.List;

public class CaveSpiderAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(4.0);
        List<LivingEntity> nearby = p.getWorld().getEntitiesByClass(LivingEntity.class, box,
            e -> e != p);
        for (LivingEntity e : nearby) {
            e.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
        }
        AbilityHelper.msg(p, "☠ Poison aura!", Formatting.DARK_GREEN);
    }
}
