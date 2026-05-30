package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class RavagerAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(6.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.damage(p.getDamageSources().mobAttack(p), 15.0f);
            e.addVelocity(
                e.getX() - p.getX(), 0.5,
                e.getZ() - p.getZ()
            );
            e.velocityModified = true;
        }
        AbilityHelper.msg(p, "🦕 RAVAGE!", Formatting.DARK_RED);
    }
}
