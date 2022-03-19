package com.exdrill.ce;

import com.exdrill.ce.registry.*;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static final String NAMESPACE = "ce";

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModParticles.registerParticles();
        ModBlocks.registerBlockEntities();
        ModSounds.registerSounds();
    }
}
