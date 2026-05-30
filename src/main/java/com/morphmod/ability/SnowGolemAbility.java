package com.morphmod.ability;

import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class SnowGolemAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        for (int i = 0; i < 5; i++) {
            double spread = (Math.random()-0.5) * 0.4;
            SnowballEntity snowball = new SnowballEntity(p.getWorld(), p);
            snowball.setVelocity(look.x + spread, look.y + spread * 0.5, look.z + spread, 2.5f, 0f);
            p.getWorld().spawnEntity(snowball);
        }
        AbilityHelper.msg(p, "⛄ Snowball Barrage!", Formatting.WHITE);
    }
}
