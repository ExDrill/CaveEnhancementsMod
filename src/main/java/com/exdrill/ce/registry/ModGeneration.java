package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.worldgen.feature.SpiralFeature;
import com.exdrill.ce.worldgen.feature.config.SpiralFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModGeneration {
    private static final Feature<SpiralFeatureConfig> SPIRAL = new SpiralFeature(SpiralFeatureConfig.CODEC);

    public static final ConfiguredFeature<?, ?> STONE_SPIRAL = SPIRAL.configure(new SpiralFeatureConfig(ConstantIntProvider.create(15), BlockStateProvider.of(Blocks.STONE.getDefaultState())));

    public static final PlacedFeature STONE_SPIRAL_PLACED = STONE_SPIRAL.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static void registerFeatures(){
        Registry.register(Registry.FEATURE, new Identifier(Main.NAMESPACE, "spiral"), SPIRAL);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.NAMESPACE, "spiral"), STONE_SPIRAL);
    }
}
