package com.exdrill.ce.mixin;

import com.exdrill.ce.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public class BiomeGeneration {
    private final MultiNoiseUtil.ParameterRange defaultParameter = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);
    @Inject(method="writeCaveBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, this.defaultParameter, MultiNoiseUtil.ParameterRange.of(0.8F, 1.0F), this.defaultParameter, this.defaultParameter, 0.0F, BiomeKeys.DRIPSTONE_CAVES);
        this.writeCaveBiomeParameters(parameters, this.defaultParameter, MultiNoiseUtil.ParameterRange.of(0.7F, 1.0F), this.defaultParameter, this.defaultParameter, this.defaultParameter, 0.0F, BiomeKeys.LUSH_CAVES);
        this.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(0.45F, 1F), this.defaultParameter, MultiNoiseUtil.ParameterRange.of(-1F, -0.35F),MultiNoiseUtil.ParameterRange.of(-0.5F, 0.5F), MultiNoiseUtil.ParameterRange.of(-0.75F, 0.5F), 0.0F, ModBiomes.ROSE_QUARTZ_CAVES_KEY);
        this.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-1F, 0.315F), MultiNoiseUtil.ParameterRange.of(-0.4F, 0.5F), MultiNoiseUtil.ParameterRange.of(0.32F, 0.72F), MultiNoiseUtil.ParameterRange.of(-0.65F, 0.78F), MultiNoiseUtil.ParameterRange.of(0.8F, 0.875F), 0.0F, ModBiomes.GOOP_CAVES_KEY);
    }
    private void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.2F, 0.9F), weirdness, offset), biome));
    }
}