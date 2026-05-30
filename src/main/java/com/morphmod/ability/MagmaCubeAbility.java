package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class MagmaCubeAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(4.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.setOnFireFor(5);
            e.damage(p.getDamageSources().onFire(), 4.0f);
        }
        AbilityHelper.msg(p, "🔥 Magma Burst!", Formatting.RED);
    }
}
