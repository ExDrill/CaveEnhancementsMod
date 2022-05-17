package com.exdrill.ce;

import com.exdrill.ce.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CaveEnhancements implements ModInitializer {
    public static final String NAMESPACE = "ce";

    public static final BannerPattern GOOP = new BannerPattern("goop");

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


        Registry.register(Registry.BANNER_PATTERN, new Identifier(NAMESPACE, "goop"), GOOP);

    }


}

