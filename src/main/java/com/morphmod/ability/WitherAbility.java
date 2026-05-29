package com.morphmod.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.*;

/** Wither: applies wither effect to nearby entities. */
public class WitherAbility {
    private static final Map<UUID, Integer> cooldowns = new HashMap<>();

    public static void tick(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int cd = cooldowns.getOrDefault(id, 0);
        if (cd > 0) { cooldowns.put(id, cd - 1); return; }
        player.getServerWorld().getEntitiesByClass(LivingEntity.class,
            player.getBoundingBox().expand(5), e -> e != player)
            .forEach(e -> e.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 1)));
        cooldowns.put(id, 60);
    }
}
