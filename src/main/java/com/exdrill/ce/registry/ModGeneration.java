package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.worldgen.feature.SpiralFeature;
import com.exdrill.ce.worldgen.feature.config.SpiralFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModGeneration {
    private static final Feature<SpiralFeatureConfig> SPIRAL = new SpiralFeature(SpiralFeatureConfig.CODEC);

    public static final ConfiguredFeature<?, ?> STONE_SPIRAL = SPIRAL.configure(new SpiralFeatureConfig(ConstantIntProvider.create(15), BlockStateProvider.of(Blocks.STONE.getDefaultState())));

    public static void registerFeatures(){
        Registry.register(Registry.FEATURE, new Identifier(Main.NAMESPACE, "spiral"), SPIRAL);

        RegistryKey<ConfiguredFeature<?, ?>> stoneSpiral = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(Main.NAMESPACE, "stone_spiral"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, stoneSpiral.getValue(), STONE_SPIRAL);

        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, BuiltinRegistries.CONFIGURED_FEATURE.getId(STONE_SPIRAL)));
    }
}
