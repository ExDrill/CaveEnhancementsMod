package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.world.features.MountainShrineFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModGeneration {
    public static final ConfiguredFeature<?, ?> ORE_LIMESTONE = ConfiguredFeatures.register("ore_limestone", Feature.ORE.configure(new OreFeatureConfig(OreConfiguredFeatures.BASE_STONE_OVERWORLD, ModBlocks.LIGHTNING_ANCHOR.getDefaultState(), 10)));

    private static final StructureFeature<DefaultFeatureConfig> MOUNTAIN_SHRINE_FEATURE = new MountainShrineFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> MOUNTAIN_SHRINE_CONFIGURED = MOUNTAIN_SHRINE_FEATURE.configure(DefaultFeatureConfig.DEFAULT);

    public static void registerFeatures(){
        RegistryKey<PlacedFeature> oreLimestoneLower = RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(Main.NAMESPACE, "ore_limestone_lower"));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, oreLimestoneLower.getValue(), ORE_LIMESTONE.withPlacement(modifiersWithCount(200, HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(256)))));
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreLimestoneLower);

        FabricStructureBuilder.create(new Identifier("tutorial", "my_structure"), MOUNTAIN_SHRINE_FEATURE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(32, 8, 12345)
                .adjustsSurface()
                .register();

        RegistryKey<ConfiguredStructureFeature<?, ?>> mountainShrineConfigured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                new Identifier(Main.NAMESPACE, "mountain_shrine"));
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, mountainShrineConfigured.getValue(), MOUNTAIN_SHRINE_CONFIGURED);

        System.out.println("Registered Features");
    }

    private static List<PlacementModifier> modifiers(PlacementModifier first, PlacementModifier second) {
        return List.of(first, SquarePlacementModifier.of(), second, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier modifier) {
        return modifiers(CountPlacementModifier.of(count), modifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier modifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), modifier);
    }
}
