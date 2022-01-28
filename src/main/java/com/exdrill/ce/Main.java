package com.exdrill.ce;

import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModItems;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static final String NAMESPACE = "ce";

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModParticles.registerParticles();
    }
}
