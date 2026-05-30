package com.morphmod.ability;

import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class WitherAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        WitherSkullEntity skull = new WitherSkullEntity(
            p.getWorld(), p,
            look.x * 0.5, look.y * 0.5, look.z * 0.5
        );
        skull.setPos(p.getX() + look.x, p.getEyeY(), p.getZ() + look.z);
        p.getWorld().spawnEntity(skull);
        AbilityHelper.msg(p, "💀 Wither Skull!", Formatting.DARK_GRAY);
    }
}
