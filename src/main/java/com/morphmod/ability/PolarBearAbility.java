package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class PolarBearAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(4.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) e.damage(p.getDamageSources().mobAttack(p), 12.0f);
        AbilityHelper.msg(p, "🐻 Bear Swipe!", Formatting.WHITE);
    }
}
