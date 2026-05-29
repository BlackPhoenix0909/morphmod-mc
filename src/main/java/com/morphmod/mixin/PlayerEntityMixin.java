package com.morphmod.mixin;

import com.morphmod.MorphData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    /**
     * Spider / Cave Spider: allow wall climbing.
     */
    @Inject(method = "isClimbing", at = @At("RETURN"), cancellable = true)
    private void morphmod_isClimbing(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        MorphData.getActiveMorphType(self.getUuid()).ifPresent(type -> {
            if (type == EntityType.SPIDER || type == EntityType.CAVE_SPIDER) {
                if (self.horizontalCollision) {
                    cir.setReturnValue(true);
                }
            }
        });
    }

    /**
     * Chicken: slow fall.
     */
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void morphmod_handleFallDamage(float fallDistance, float damageMultiplier,
                                            net.minecraft.entity.damage.DamageSource source,
                                            CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        MorphData.getActiveMorphType(self.getUuid()).ifPresent(type -> {
            if (type == EntityType.CHICKEN || type == EntityType.PARROT) {
                cir.setReturnValue(false); // No fall damage
            }
        });
    }
}
