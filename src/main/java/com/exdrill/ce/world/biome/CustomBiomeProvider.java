package com.exdrill.ce.world.biome;


import com.exdrill.ce.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CustomBiomeProvider extends Region {


    public CustomBiomeProvider(Identifier name, RegionType type, int weight) {
        super(name, type, weight);
    }

    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(BiomeKeys.DRIPSTONE_CAVES, ModBiomes.GOOP_CAVES_KEY);
            builder.replaceBiome(BiomeKeys.LUSH_CAVES, ModBiomes.ROSE_QUARTZ_CAVES_KEY);
        });
        //this.addBiome(mapper, MultiNoiseUtil.createNoiseHypercube(1.0F, 1F, 0.4F, 1, 1, 1, 1), ModBiomes.GOOP_CAVES_KEY);

    }
}
