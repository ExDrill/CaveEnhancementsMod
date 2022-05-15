package com.exdrill.ce;

import com.exdrill.ce.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class CaveEnhancements implements ModInitializer {
    public static final String NAMESPACE = "ce";

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModParticles.registerParticles();
        ModBlocks.registerBlockEntities();
        ModSounds.registerSounds();
        ModEntities.registerEntities();
        ModBiomes.registerBiomes();
        ModBiomes.registerBiomeModifications();
    }


}

