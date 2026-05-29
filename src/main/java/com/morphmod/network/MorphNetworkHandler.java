package com.morphmod.network;

import com.morphmod.MorphData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Set;

public class MorphNetworkHandler {
    public static final Identifier MORPH_UPDATE_PACKET  = new Identifier("morphmod", "morph_update");
    public static final Identifier SYNC_MORPHS_PACKET   = new Identifier("morphmod", "sync_morphs");
    public static final Identifier OPEN_GUI_PACKET      = new Identifier("morphmod", "open_gui");
    public static final Identifier REQUEST_MORPH_PACKET = new Identifier("morphmod", "request_morph");

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_MORPH_PACKET, (server, player, handler, buf, responseSender) -> {
            String morphId = buf.readString();
            server.execute(() -> {
                if (morphId.equals("player")) {
                    MorphData.setActiveMorph(player.getUuid(), null);
                    sendMorphUpdate(player, null);
                } else {
                    Identifier id = new Identifier(morphId);
                    net.minecraft.registry.Registries.ENTITY_TYPE.getOrEmpty(id).ifPresent(type -> {
                        if (MorphData.hasMorph(player.getUuid(), type)) {
                            MorphData.setActiveMorph(player.getUuid(), morphId);
                            sendMorphUpdate(player, morphId);
                        }
                    });
                }
            });
        });
    }

    public static void sendMorphUpdate(ServerPlayerEntity player, String morphId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(player.getUuid());
        buf.writeString(morphId != null ? morphId : "player");
        for (ServerPlayerEntity other : player.getServer().getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(other, MORPH_UPDATE_PACKET, PacketByteBufs.copy(buf));
        }
    }

    public static void sendSyncToPlayer(ServerPlayerEntity player) {
        Set<String> morphs = MorphData.getUnlockedMorphs(player.getUuid());
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(morphs.size());
        for (String morph : morphs) {
            buf.writeString(morph);
        }
        ServerPlayNetworking.send(player, SYNC_MORPHS_PACKET, buf);
    }

    public static void sendOpenGui(ServerPlayerEntity player) {
        sendSyncToPlayer(player);
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, OPEN_GUI_PACKET, buf);
    }
}
