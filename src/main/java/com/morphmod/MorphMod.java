package com.morphmod;

import com.morphmod.network.MorphNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MorphMod implements ModInitializer {
    public static final String MOD_ID = "morphmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("MorphMod initializing...");
        MorphNetworkHandler.registerServerPackets();
        MorphEventHandler.register();
        MorphCommands.register();
        LOGGER.info("MorphMod initialized!");
    }
}
