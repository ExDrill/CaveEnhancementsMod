package com.exdrill.cave_enhancements;

import com.exdrill.cave_enhancements.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CaveEnhancements implements ModInitializer {
    public static final String NAMESPACE = "cave_enhancements";

    public static final BannerPattern GOOP = new BannerPattern("goop");

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModParticles.register();
        ModBlocks.registerBlockEntities();
        ModSounds.register();
        ModEntities.register();
        ModBiomes.register();
        ModBiomes.registerModifications();
        ModStatusEffects.register();
        ModBlocks.registerOxidizablePairs();

        Registry.register(Registry.BANNER_PATTERN, new Identifier(NAMESPACE, "goop"), GOOP);

    }


}

