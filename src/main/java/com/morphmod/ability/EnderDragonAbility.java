package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class EnderDragonAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(20.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.damage(p.getDamageSources().magic(), 20.0f);
        }
        AbilityHelper.applyEffect(p, StatusEffects.RESISTANCE, 100, 4);
        AbilityHelper.applyEffect(p, StatusEffects.STRENGTH, 100, 4);
        AbilityHelper.msg(p, "🐉 Dragon's Wrath!", Formatting.DARK_PURPLE);
    }
}
