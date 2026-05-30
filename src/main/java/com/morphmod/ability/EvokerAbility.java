package com.morphmod.ability;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class EvokerAbility {
    public static void trigger(ServerPlayerEntity p) {
        for (int i = 0; i < 3; i++) {
            VexEntity vex = EntityType.VEX.create(p.getWorld());
            if (vex != null) {
                vex.setPos(
                    p.getX() + (Math.random() - 0.5) * 2,
                    p.getY() + 1,
                    p.getZ() + (Math.random() - 0.5) * 2
                );
                vex.setLifeTicks(20 * 30);
                p.getWorld().spawnEntity(vex);
            }
        }
        AbilityHelper.msg(p, "🧙 Summoned Vexes!", Formatting.LIGHT_PURPLE);
    }
}
