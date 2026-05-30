package com.morphmod.ability;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
public class DrownedAbility {
    public static void trigger(ServerPlayerEntity p) {
        AbilityHelper.applyEffect(p, StatusEffects.WATER_BREATHING, 400, 0);
        AbilityHelper.applyEffect(p, StatusEffects.DOLPHINS_GRACE, 200, 0);
        AbilityHelper.msg(p, "🌊 Water Breathing + Speed!", Formatting.AQUA);
    }
}
