package com.morphmod.ability;

import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class GhastAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        FireballEntity fireball = new FireballEntity(
            p.getWorld(), p,
            look.x * 2, look.y * 2, look.z * 2,
            1  // explosion power
        );
        fireball.setPos(p.getX() + look.x * 2, p.getEyeY(), p.getZ() + look.z * 2);
        p.getWorld().spawnEntity(fireball);
        AbilityHelper.msg(p, "💀 Ghast Fireball!", Formatting.RED);
    }
}
