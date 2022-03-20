package com.exdrill.ce.world.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.UndergroundPlacedFeatures;

public class CustomFeatures {

    public static void addGoopCavesDecorations(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.LUSH_CAVES_CEILING_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.CAVE_VINES);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.LUSH_CAVES_CLAY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.LUSH_CAVES_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.ROOTED_AZALEA_TREE);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.SPORE_BLOSSOM);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.CLASSIC_VINES_CAVE_FEATURE);
    }
}
