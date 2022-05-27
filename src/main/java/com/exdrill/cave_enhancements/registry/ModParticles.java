package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.particle.*;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public static final DefaultParticleType SMALL_GOOP_DRIP = FabricParticleTypes.simple();
    public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();
    public static final DefaultParticleType ROSE_QUARTZ_AURA = FabricParticleTypes.simple();
    public static final DefaultParticleType SOOTHINGNOTE = FabricParticleTypes.simple();
    public static final DefaultParticleType ROSE_CHIMES = FabricParticleTypes.simple();
    public static final DefaultParticleType AMETHYST_BLAST = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "small_goop_drip"), SMALL_GOOP_DRIP);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "shockwave"), SHOCKWAVE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_aura"), ROSE_QUARTZ_AURA);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "soothing_note"), SOOTHINGNOTE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "rose_chimes"), ROSE_CHIMES);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "amethyst_blast"), AMETHYST_BLAST);
    }

    public static void registerClient() {
        // Small Goop Drip Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/small_goop_drip"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_GOOP_DRIP, SmallGoopDrip.SmallGoopDripFactory::new);

        // Shockwave Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/shockwave"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE, Shockwave.Factory::new);

        // Rose Quartz Aura Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/rose_quartz_aura"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_QUARTZ_AURA, RoseQuartzAura.RoseQuartzFactory::new);

        // Soothing Note Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/soothing_note"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOTHINGNOTE, SoothingNote.SoothingNoteFactory::new);

        // Rose Chimes Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/rose_chimes"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_CHIMES, RoseChimes.RoseChimesFactory::new);

        // Amethyst Blast Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(CaveEnhancements.NAMESPACE, "particle/amethyst_blast"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_BLAST, AmethystBlastParticle.Factory::new);
    }
}
