package com.exdrill.ce.registry;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.world.biome.CaveBiomes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static final RegistryKey<Biome> GOOP_CAVES_KEY = registerBiomeKeys("goop_caves");
    public static final RegistryKey<Biome> ROSE_QUARTZ_CAVES_KEY = registerBiomeKeys("rose_quartz_caves");

    private static RegistryKey<Biome> registerBiomeKeys(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(CaveEnhancements.NAMESPACE, name));
    }

    public static void registerBiomes() {
        register(GOOP_CAVES_KEY, CaveBiomes.createGoopCaves());
        register(ROSE_QUARTZ_CAVES_KEY, CaveBiomes.createRoseQuartzCaves());
    }


    private static RegistryEntry<Biome> register(RegistryKey<Biome> key, Biome biome) {
        return BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }
}
