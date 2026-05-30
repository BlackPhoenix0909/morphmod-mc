package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class HuskAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(3.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) e.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 1));
        AbilityHelper.msg(p, "🏜 Desert Hunger!", Formatting.YELLOW);
    }
}
