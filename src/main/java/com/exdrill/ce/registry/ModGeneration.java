package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.world.structures.MountainShrine;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModGeneration {
    public static StructureFeature<StructurePoolFeatureConfig> MOUNTAIN_SHRINE = new MountainShrine(StructurePoolFeatureConfig.CODEC);

    public static ConfiguredStructureFeature<?, ?> CONFIGURED_MOUNTAIN_SHRINE = MOUNTAIN_SHRINE
            .configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

    public static void registerFeatures() {
        FabricStructureBuilder.create(new Identifier(Main.NAMESPACE, "mountain_shrine"), MOUNTAIN_SHRINE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(
                        500, /* average distance apart in chunks between spawn attempts */
                        300, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
                        399117346 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))
                .adjustsSurface()
                .register();

        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new Identifier(Main.NAMESPACE, "configured_mountain_shrine"), CONFIGURED_MOUNTAIN_SHRINE);

        BiomeModifications.addStructure(
                BiomeSelectors.includeByKey(BiomeKeys.MEADOW),

                RegistryKey.of(
                        Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_MOUNTAIN_SHRINE))
        );
    }
}
