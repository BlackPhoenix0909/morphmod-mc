package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class GuardianAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(10.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) e.damage(p.getDamageSources().magic(), 8.0f);
        AbilityHelper.applyEffect(p, StatusEffects.WATER_BREATHING, 400, 0);
        AbilityHelper.msg(p, "🔱 Laser Beam!", Formatting.AQUA);
    }
}
