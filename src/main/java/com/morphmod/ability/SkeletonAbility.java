package com.morphmod.ability;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class SkeletonAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        for (int i = 0; i < 5; i++) {
            double spread = (Math.random() - 0.5) * 0.3;
            ArrowEntity arrow = new ArrowEntity(EntityType.ARROW, p.getWorld());
            arrow.setOwner(p);
            arrow.setPos(p.getX(), p.getEyeY(), p.getZ());
            arrow.setVelocity(look.x + spread, look.y + spread * 0.5, look.z + spread, 3.0f, 0f);
            p.getWorld().spawnEntity(arrow);
        }
        AbilityHelper.msg(p, "🏹 Arrow Volley!", Formatting.WHITE);
    }
}
