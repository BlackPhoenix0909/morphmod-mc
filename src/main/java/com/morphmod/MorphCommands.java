package com.morphmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.morphmod.network.MorphNetworkHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityTypeArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class MorphCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            // /morph <entity_type> - transform into a mob (requires unlock)
            dispatcher.register(CommandManager.literal("morph")
                .then(CommandManager.argument("entity", StringArgumentType.string())
                    .executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                        String entityId = StringArgumentType.getString(ctx, "entity");
                        return morphPlayer(player, entityId);
                    })
                )
                .executes(ctx -> {
                    // Open morph GUI
                    ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                    MorphNetworkHandler.sendOpenGui(player);
                    return 1;
                })
            );

            // /morph unmorph - return to human
            dispatcher.register(CommandManager.literal("unmorph")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                    MorphData.setActiveMorph(player.getUuid(), null);
                    MorphNetworkHandler.sendMorphUpdate(player, null);
                    player.sendMessage(Text.literal("You returned to your human form.").formatted(Formatting.GREEN), false);
                    return 1;
                })
            );

            // /morphgive <player> <entity> - admin command to give morph
            dispatcher.register(CommandManager.literal("morphgive")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.argument("entity", StringArgumentType.string())
                    .executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                        String entityId = StringArgumentType.getString(ctx, "entity");
                        Identifier id = new Identifier(entityId);
                        Registries.ENTITY_TYPE.getOrEmpty(id).ifPresent(type -> {
                            MorphData.unlockMorph(player.getUuid(), type);
                            MorphNetworkHandler.sendSyncToPlayer(player);
                            player.sendMessage(Text.literal("Morph granted: " + entityId).formatted(Formatting.GREEN), false);
                        });
                        return 1;
                    })
                )
            );
        });
    }

    private static int morphPlayer(ServerPlayerEntity player, String entityId) {
        Identifier id = new Identifier(entityId);
        var entityTypeOpt = Registries.ENTITY_TYPE.getOrEmpty(id);
        if (entityTypeOpt.isEmpty()) {
            player.sendMessage(Text.literal("Unknown entity: " + entityId).formatted(Formatting.RED), false);
            return 0;
        }
        EntityType<?> entityType = entityTypeOpt.get();
        if (!MorphData.hasMorph(player.getUuid(), entityType)) {
            player.sendMessage(Text.literal("You haven't killed a " + entityType.getName().getString() + " yet!")
                .formatted(Formatting.RED), false);
            return 0;
        }
        MorphData.setActiveMorph(player.getUuid(), id.toString());
        MorphNetworkHandler.sendMorphUpdate(player, id.toString());
        player.sendMessage(Text.literal("Morphed into: ").formatted(Formatting.GOLD)
            .append(Text.literal(entityType.getName().getString()).formatted(Formatting.YELLOW)), false);
        return 1;
    }
}
