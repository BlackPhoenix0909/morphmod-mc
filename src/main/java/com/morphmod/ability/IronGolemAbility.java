package com.morphmod.ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import java.util.List;
public class IronGolemAbility {
    public static void trigger(ServerPlayerEntity p) {
        Box box = p.getBoundingBox().expand(5.0);
        List<LivingEntity> targets = p.getWorld().getEntitiesByClass(LivingEntity.class, box, e -> e != p);
        for (LivingEntity e : targets) {
            e.damage(p.getDamageSources().mobAttack(p), 20.0f);
            e.addVelocity(0, 2.0, 0);
            e.velocityModified = true;
        }
        AbilityHelper.msg(p, "⚙ Iron Fist! Launch!", Formatting.GRAY);
    }
}
