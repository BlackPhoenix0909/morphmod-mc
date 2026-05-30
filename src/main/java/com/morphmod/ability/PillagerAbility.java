package com.morphmod.ability;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class PillagerAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        ArrowEntity arrow = new ArrowEntity(EntityType.ARROW, p.getWorld());
        arrow.setOwner(p);
        arrow.setPos(p.getX(), p.getEyeY(), p.getZ());
        arrow.setVelocity(look.x * 4, look.y * 4, look.z * 4, 1.5f, 0f);
        arrow.setPierceLevel((byte) 3);
        p.getWorld().spawnEntity(arrow);
        AbilityHelper.msg(p, "🏹 Piercing Arrow!", Formatting.DARK_RED);
    }
}
