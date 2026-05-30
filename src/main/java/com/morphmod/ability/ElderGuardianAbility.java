package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class ElderGuardianAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(15.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 300, 2));
            e.damage(p.getDamageSources().magic(), 12.0f);
        }
        AbilityHelper.msg(p, "⚓ Elder Curse! Mining Fatigue + Damage!", Formatting.DARK_AQUA);
    }
}
