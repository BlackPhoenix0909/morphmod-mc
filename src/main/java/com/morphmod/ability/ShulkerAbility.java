package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
public class ShulkerAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        ShulkerBulletEntity bullet = new ShulkerBulletEntity(
            p.getWorld(), null, p,
            net.minecraft.util.math.Direction.UP
        );
        bullet.setPos(p.getX() + look.x * 2, p.getEyeY(), p.getZ() + look.z * 2);
        p.getWorld().spawnEntity(bullet);
        AbilityHelper.msg(p, "📦 Shulker Bullet!", Formatting.LIGHT_PURPLE);
    }
}
