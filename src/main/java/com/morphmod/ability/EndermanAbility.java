package com.morphmod.ability;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class EndermanAbility {
    public static void trigger(ServerPlayerEntity p) {
        Vec3d look = p.getRotationVec(1.0f).multiply(16);
        double tx = p.getX() + look.x;
        double ty = p.getY() + look.y;
        double tz = p.getZ() + look.z;
        p.teleport(tx, ty, tz);
        AbilityHelper.msg(p, "👁 Teleported!", Formatting.DARK_PURPLE);
    }
}
