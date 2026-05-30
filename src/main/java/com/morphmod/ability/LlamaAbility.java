package com.morphmod.ability;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
public class LlamaAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f);
        LlamaSpitEntity spit = new LlamaSpitEntity(p.getWorld(), null);
        spit.setOwner(p);
        spit.setPos(p.getX() + look.x, p.getEyeY(), p.getZ() + look.z);
        spit.setVelocity(look.x * 2, look.y * 2, look.z * 2);
        p.getWorld().spawnEntity(spit);
        AbilityHelper.msg(p, "🦙 Spit!", Formatting.YELLOW);
    }
}
