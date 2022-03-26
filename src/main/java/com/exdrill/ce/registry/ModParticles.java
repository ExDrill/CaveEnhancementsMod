package com.exdrill.ce.registry;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.particle.RoseQuartzAura;
import com.exdrill.ce.particle.Shockwave;
import com.exdrill.ce.particle.SmallGoopDrip;
import com.exdrill.ce.particle.SoothingNote;
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

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "small_goop_drip"), SMALL_GOOP_DRIP);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "shockwave"), SHOCKWAVE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_aura"), ROSE_QUARTZ_AURA);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "soothing_note"), SOOTHINGNOTE);
    }

    public static void registerClientParticles() {
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
    }
}
