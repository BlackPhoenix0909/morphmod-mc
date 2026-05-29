package com.morphmod.mixin;

import com.morphmod.MorphData;
import com.morphmod.ability.MobAbilityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    /**
     * Apply morph-based attack damage when player attacks.
     */
    @Inject(method = "damage", at = @At("HEAD"))
    private void morphmod_applyMorphDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Handled by stats applied each tick; this is where extra logic could go
    }
}
