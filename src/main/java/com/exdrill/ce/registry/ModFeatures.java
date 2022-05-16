package com.exdrill.ce.registry;

import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModFeatures {

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_GOOP;

    static {
        ORE_GOOP = ConfiguredFeatures.register("ce:ore_goop", Feature.ORE, new OreFeatureConfig(new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD), ModBlocks.GOOP_BLOCK.getDefaultState(), 33));
    }

}
