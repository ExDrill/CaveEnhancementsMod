package com.exdrill.cave_enhancements.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.PlacedFeature;

import static com.exdrill.cave_enhancements.CaveEnhancements.MODID;

public class ModFeatures {

    public static void register() {
        RegistryKey<PlacedFeature> ORE_GOOP = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "ore_goop"));
        RegistryKey<PlacedFeature> DRIPPING_GOOP_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "dripping_goop_feature"));
        RegistryKey<PlacedFeature> GOOP_CAVES_PATCH_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "goop_caves_patch_feature"));
        RegistryKey<PlacedFeature> GOOP_SPLAT_CEILING_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "goop_splat_ceiling_feature"));
        RegistryKey<PlacedFeature> GOOP_SPLAT_FLOOR_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "goop_splat_floor_feature"));

        RegistryKey<PlacedFeature> JAGGED_ROSE_QUARTZ_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "jagged_rose_quartz_feature"));
        RegistryKey<PlacedFeature> ROSE_QUARTZ_PATCH_DOWN_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "rose_quartz_patch_down_feature"));
        RegistryKey<PlacedFeature> ROSE_QUARTZ_PATCH_FEATURE = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "rose_quartz_patch_feature"));
    }

}
