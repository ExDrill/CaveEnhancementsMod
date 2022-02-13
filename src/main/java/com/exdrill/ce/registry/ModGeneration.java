package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.world.structures.RunDownHouseStructure;
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
    public static StructureFeature<StructurePoolFeatureConfig> RUN_DOWN_HOUSE = new RunDownHouseStructure(StructurePoolFeatureConfig.CODEC);

    public static ConfiguredStructureFeature<?, ?> CONFIGURED_RUN_DOWN_HOUSE = RUN_DOWN_HOUSE
            .configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

    public static void registerFeatures() {
        FabricStructureBuilder.create(new Identifier(Main.NAMESPACE, "run_down_house"), RUN_DOWN_HOUSE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(
                        10, /* average distance apart in chunks between spawn attempts */
                        5, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
                        399117345 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))
                .adjustsSurface()
                .register();

        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new Identifier(Main.NAMESPACE, "configured_run_down_house"), CONFIGURED_RUN_DOWN_HOUSE);

        BiomeModifications.addStructure(
                BiomeSelectors.includeByKey(BiomeKeys.MEADOW),

                RegistryKey.of(
                        Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_RUN_DOWN_HOUSE))
        );
    }
}
