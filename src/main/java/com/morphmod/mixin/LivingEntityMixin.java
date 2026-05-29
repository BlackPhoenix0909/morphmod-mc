package com.morphmod.mixin;

import com.morphmod.MorphData;
import com.morphmod.ability.MobAbilityRegistry;
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
     * Override attack damage for morphed players.
     * We hook getAttributeValue(EntityAttribute) — the single-arg variant.
     * In 1.20.4 the intermediary name is getAttributeValue and takes
     * net.minecraft.entity.attribute.EntityAttribute (class_6880).
     *
     * To avoid descriptor mismatch we use the Yarn name WITHOUT a descriptor
     * and let Mixin resolve it at runtime.
     */
    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D",
            at = @At("RETURN"), cancellable = true)
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
