package com.morphmod.ability;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
public class StrayAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        ArrowEntity arrow = new ArrowEntity(p.getWorld(), p);
        arrow.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1));
        arrow.setVelocity(look.x * 3, look.y * 3, look.z * 3, 1.5f, 0f);
        arrow.setPos(p.getX(), p.getEyeY(), p.getZ());
        p.getWorld().spawnEntity(arrow);
        AbilityHelper.msg(p, "❄ Slowness Arrow!", Formatting.AQUA);
    }
}
