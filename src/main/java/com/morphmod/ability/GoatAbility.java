package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class GoatAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(3.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.damage(p.getDamageSources().mobAttack(p), 6.0f);
            e.addVelocity(p.getRotationVec(1.0f).x * 2, 0.5, p.getRotationVec(1.0f).z * 2);
            e.velocityModified = true;
        }
        AbilityHelper.msg(p, "🐐 Ram!", Formatting.WHITE);
    }
}
