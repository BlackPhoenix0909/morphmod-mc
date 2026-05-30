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
        // Get morph for this player
        String morphId = MorphRenderState.getMorph(player.getUuid());
        if (morphId == null || morphId.equals("player")) return;

        try {
            Identifier id = new Identifier(morphId);
            EntityType<?> type = Registries.ENTITY_TYPE.getOrEmpty(id).orElse(null);
            if (type == null) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();

            // Create a temporary entity of the mob type to render
            Entity fakeEntity = type.create(mc.world);
            if (fakeEntity == null) return;

            // Position it where the player is
            fakeEntity.setPos(player.getX(), player.getY(), player.getZ());
            fakeEntity.setYaw(player.getYaw());
            fakeEntity.setPitch(player.getPitch());
            fakeEntity.age = player.age;

            // Render the mob entity instead of the player
            dispatcher.render(fakeEntity, 0, 0, 0, yaw, tickDelta, matrices, vertexConsumers, light);

            // Cancel normal player rendering
            ci.cancel();
        } catch (Exception e) {
            // If anything fails, fall back to normal player rendering
        }
    }
}
