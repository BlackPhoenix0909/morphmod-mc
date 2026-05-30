package com.morphmod.client.mixin;

import com.morphmod.client.MorphModClient;
import com.morphmod.client.MorphRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WaterAnimalEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void morphmod_renderAsMob(AbstractClientPlayerEntity player,
                                       float yaw, float tickDelta,
                                       MatrixStack matrices,
                                       VertexConsumerProvider vertexConsumers,
                                       int light,
                                       CallbackInfo ci) {
        String morphId = MorphRenderState.getMorph(player.getUuid());
        if (morphId == null || morphId.equals("player")) return;

        try {
            Identifier id = new Identifier(morphId);
            EntityType<?> type = Registries.ENTITY_TYPE.getOrEmpty(id).orElse(null);
            if (type == null) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();

            Entity fakeEntity = type.create(mc.world);
            if (fakeEntity == null) return;

            // --- Position & Rotation ---
            fakeEntity.setPos(player.getX(), player.getY(), player.getZ());
            fakeEntity.setYaw(player.getYaw());
            fakeEntity.setPitch(player.getPitch());
            fakeEntity.age = player.age;

            // --- Animationszustand vom Player übertragen ---
            if (fakeEntity instanceof LivingEntity fakeLiving) {

                // Körper- und Kopfrotation synchronisieren
                fakeLiving.bodyYaw     = player.bodyYaw;
                fakeLiving.prevBodyYaw = player.prevBodyYaw;
                fakeLiving.headYaw     = player.headYaw;
                fakeLiving.prevHeadYaw = player.prevHeadYaw;

                // Lauf-Animation (Bein-Schwingen)
                fakeLiving.limbAnimator.setSpeed(player.limbAnimator.getSpeed());
                fakeLiving.limbAnimator.pos   = player.limbAnimator.pos;

                // Hurt-Zustand (Blinken bei Schaden)
                fakeLiving.hurtTime    = player.hurtTime;
                fakeLiving.deathTime   = player.deathTime;

                // Schwimm-Animation
                fakeLiving.setSwimming(player.isSwimming());

                // Sneak-Pose weitergeben
                fakeLiving.setPose(player.getPose());

                // Wasser-/Flug-Tiere: in Wasser tauchen wenn Spieler im Wasser ist
                if (fakeEntity instanceof WaterAnimalEntity waterFake) {
                    waterFake.setTouchingWater(player.isTouchingWater());
                }

                // Velocity übertragen (wichtig für Flatter-/Flug-Animationen)
                fakeLiving.setVelocity(player.getVelocity());
                fakeLiving.prevX = player.prevX;
                fakeLiving.prevY = player.prevY;
                fakeLiving.prevZ = player.prevZ;

                // On-Ground für Sprung-Animation
                fakeLiving.setOnGround(player.isOnGround());
            }

            dispatcher.render(fakeEntity, 0, 0, 0, yaw, tickDelta, matrices, vertexConsumers, light);

            // Fake-Entity aufräumen damit kein Speicherleck entsteht
            fakeEntity.discard();

            ci.cancel();
        } catch (Exception e) {
            // Fallback: normales Player-Rendering
        }
    }
}
