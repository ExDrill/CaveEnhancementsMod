package com.exdrill.ce.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record SpiralFeatureConfig(IntProvider height, BlockStateProvider block) implements FeatureConfig {
    public static final Codec<SpiralFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.VALUE_CODEC.fieldOf("height").forGetter(SpiralFeatureConfig::height),
            BlockStateProvider.TYPE_CODEC.fieldOf("block").forGetter(SpiralFeatureConfig::block)
    ).apply(instance, instance.stable(SpiralFeatureConfig::new)));
}
