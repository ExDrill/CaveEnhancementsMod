package com.exdrill.ce;

import com.exdrill.ce.registry.*;
import net.fabricmc.api.ModInitializer;
import terrablender.api.TerraBlenderApi;

public class CaveEnhancements implements ModInitializer, TerraBlenderApi {
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



    @Override
        public void onTerraBlenderInitialized() {
            //Regions.register(new CustomBiomeProvider(new Identifier(NAMESPACE, "custom_biome_provider"), RegionType.OVERWORLD, 10));
        }
    }

