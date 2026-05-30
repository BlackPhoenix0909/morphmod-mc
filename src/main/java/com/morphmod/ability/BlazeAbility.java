package com.morphmod.ability;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class BlazeAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        SmallFireballEntity fireball = new SmallFireballEntity(
            p.getWorld(), p,
            look.x * 1.5, look.y * 1.5, look.z * 1.5
        );
        fireball.setPos(p.getX() + look.x, p.getEyeY(), p.getZ() + look.z);
        p.getWorld().spawnEntity(fireball);
        AbilityHelper.msg(p, "🔥 Fireball!", Formatting.GOLD);
    }
}
