package com.morphmod;

import com.morphmod.ability.MobAbilityRegistry;
import com.morphmod.network.MorphNetworkHandler;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MorphEventHandler {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof ServerPlayerEntity player) {
                EntityType<?> killedType = killedEntity.getType();
                if (killedType == EntityType.PLAYER) return;

                boolean alreadyUnlocked = MorphData.hasMorph(player.getUuid(), killedType);
                MorphData.unlockMorph(player.getUuid(), killedType);

                if (!alreadyUnlocked) {
                    String mobName = killedType.getName().getString();
                    player.sendMessage(
                        Text.literal("✦ Morph unlocked: ")
                            .formatted(Formatting.GOLD)
                            .append(Text.literal(mobName).formatted(Formatting.YELLOW)),
                        true
                    );
                    MorphNetworkHandler.sendSyncToPlayer(player);
                }
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                applyMorphStats(player);
                MobAbilityRegistry.tickAbilities(player);
            }
        });
    }

    public static void applyMorphStats(ServerPlayerEntity player) {
        MorphData.getActiveMorphType(player.getUuid()).ifPresentOrElse(entityType -> {
            double mobHealth = MobAbilityRegistry.getMaxHealth(entityType);
            var maxHealthAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (maxHealthAttr != null && maxHealthAttr.getBaseValue() != mobHealth) {
                maxHealthAttr.setBaseValue(mobHealth);
                if (player.getHealth() > mobHealth) {
                    player.setHealth((float) mobHealth);
                }
            }
        }, () -> {
            var maxHealthAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (maxHealthAttr != null && maxHealthAttr.getBaseValue() != 20.0) {
                maxHealthAttr.setBaseValue(20.0);
            }
        });
    }
}
