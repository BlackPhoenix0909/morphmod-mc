package com.morphmod.mixin;

import com.morphmod.MorphData;
import com.morphmod.ability.MobAbilityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    /**
     * Override getAttackDamage for morphed players.
     */
    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void morphmod_attackDamage(net.minecraft.entity.attribute.EntityAttribute attribute,
                                        CallbackInfoReturnable<Double> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (self instanceof PlayerEntity player && attribute == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
            MorphData.getActiveMorphType(player.getUuid()).ifPresent(type -> {
                cir.setReturnValue(MobAbilityRegistry.getAttackDamage(type));
            });
        }
    }
}
