package com.morphmod.client;

import com.morphmod.client.gui.MorphScreen;
import com.morphmod.network.MorphNetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class MorphModClient implements ClientModInitializer {

    public static final Set<String> unlockedMorphs = new HashSet<>();
    public static String activeMorph = null; // null = human

    private static KeyBinding morphMenuKey;

    @Override
    public void onInitializeClient() {
        // Register keybinding (default: M)
        morphMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.morphmod.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.morphmod"
        ));

        // Open GUI on key press
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (morphMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new MorphScreen());
                }
            }
        });

        // Receive morph update from server (for this player and others)
        ClientPlayNetworking.registerGlobalReceiver(MorphNetworkHandler.MORPH_UPDATE_PACKET, (client2, handler, buf, responseSender) -> {
            UUID playerUuid = buf.readUuid();
            String morphId = buf.readString();
            client2.execute(() -> {
                if (client2.player != null && client2.player.getUuid().equals(playerUuid)) {
                    activeMorph = morphId.equals("player") ? null : morphId;
                    MorphRenderState.updateSelf(activeMorph);
                }
                MorphRenderState.updatePlayer(playerUuid, morphId);
            });
        });

        // Receive full sync of unlocked morphs
        ClientPlayNetworking.registerGlobalReceiver(MorphNetworkHandler.SYNC_MORPHS_PACKET, (client2, handler, buf, responseSender) -> {
            int count = buf.readInt();
            Set<String> received = new HashSet<>();
            for (int i = 0; i < count; i++) received.add(buf.readString());
            client2.execute(() -> {
                unlockedMorphs.clear();
                unlockedMorphs.addAll(received);
            });
        });

        // Server wants to open GUI
        ClientPlayNetworking.registerGlobalReceiver(MorphNetworkHandler.OPEN_GUI_PACKET, (client2, handler, buf, responseSender) -> {
            client2.execute(() -> client2.setScreen(new MorphScreen()));
        });
    }

    /** Send morph request to server */
    public static void requestMorph(String morphId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(morphId != null ? morphId : "player");
        ClientPlayNetworking.send(MorphNetworkHandler.REQUEST_MORPH_PACKET, buf);
    }
}
