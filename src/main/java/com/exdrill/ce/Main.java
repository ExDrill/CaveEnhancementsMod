package com.exdrill.ce;

import com.exdrill.ce.registry.*;
import com.exdrill.ce.world.biome.CustomBiomeProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class Main implements ModInitializer, TerraBlenderApi {
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
    }



    @Override
        public void onTerraBlenderInitialized() {
            Regions.register(new CustomBiomeProvider(new Identifier(NAMESPACE, "custom_biome_provider"), RegionType.OVERWORLD, 1));
        }
    }

