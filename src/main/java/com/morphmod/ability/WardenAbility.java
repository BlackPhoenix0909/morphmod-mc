package com.morphmod.ability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Warden ability: Sonic Boom - the iconic blue beam that damages through armor.
 * Fires every 40 ticks (2 seconds) at the nearest player/mob in range.
 * Right-clicking while morphed triggers the sonic boom on the target you're looking at.
 */
public class WardenAbility {
    private static final Map<UUID, Integer> cooldowns = new HashMap<>();
    private static final int COOLDOWN_TICKS = 100; // 5 seconds
    private static final double SONIC_BOOM_RANGE = 20.0;
    private static final float SONIC_BOOM_DAMAGE = 10.0f;

    public static void tick(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int cd = cooldowns.getOrDefault(id, 0);
        if (cd > 0) {
            cooldowns.put(id, cd - 1);
            return;
        }

        // Auto-aim sonic boom at nearest hostile mob within range
        ServerWorld world = player.getServerWorld();
        List<LivingEntity> nearby = world.getEntitiesByClass(LivingEntity.class,
            player.getBoundingBox().expand(SONIC_BOOM_RANGE),
            e -> e != player && !(e instanceof ServerPlayerEntity));

        if (nearby.isEmpty()) return;

        // Find closest
        LivingEntity target = null;
        double minDist = Double.MAX_VALUE;
        for (LivingEntity e : nearby) {
            double d = e.squaredDistanceTo(player);
            if (d < minDist) { minDist = d; target = e; }
        }

        if (target != null) {
            fireSonicBoom(player, target);
            cooldowns.put(id, COOLDOWN_TICKS);
        }
    }

    public static void fireSonicBoom(ServerPlayerEntity player, Entity target) {
        ServerWorld world = player.getServerWorld();

        // Spawn particle beam effect
        Vec3d start = player.getEyePos();
        Vec3d end = target.getEyePos();
        Vec3d dir = end.subtract(start).normalize();
        double dist = start.distanceTo(end);

        for (double d = 0; d < dist; d += 0.5) {
            Vec3d pos = start.add(dir.multiply(d));
            world.spawnParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0.1, 0.1, 0.1, 0.0);
        }

        // Deal damage (bypasses armor like the real warden)
        if (target instanceof LivingEntity living) {
            living.damage(world.getDamageSources().sonicBoom(player), SONIC_BOOM_DAMAGE);
        }
    }
}
