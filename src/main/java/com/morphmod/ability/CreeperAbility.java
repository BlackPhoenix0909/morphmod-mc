package com.morphmod.ability;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CreeperAbility {
    public static void trigger(ServerPlayerEntity p) {
        ServerWorld world = p.getServerWorld();
        world.createExplosion(p, p.getDamageSources().explosion(null, p),
            null,
            p.getX(), p.getY(), p.getZ(),
            3.0f, false,
            World.ExplosionSourceType.MOB);
        AbilityHelper.msg(p, "💥 BOOM!", Formatting.RED);
    }
}
