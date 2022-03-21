package com.exdrill.ce;

import com.exdrill.ce.client.model.entity.CruncherRenderer;
import com.exdrill.ce.client.model.entity.DripstoneTortoiseRenderer;
import com.exdrill.ce.client.model.entity.GoopRenderer;
import com.exdrill.ce.particle.RoseQuartzAura;
import com.exdrill.ce.particle.Shockwave;
import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModEntities;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.TransparentBlocks();

        // Small Goop Drip Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Main.NAMESPACE, "particle/small_goop_drip"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_GOOP_DRIP, BlockLeakParticle.DrippingWaterFactory::new);

        // Shockwave Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Main.NAMESPACE, "particle/shockwave"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE, Shockwave.Factory::new);

        // Rose Quartz Aura Client Particle
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Main.NAMESPACE, "particle/rose_quartz_aura"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_QUARTZ_AURA, RoseQuartzAura.RoseQuartzFactory::new);

        // Entity Renderer Registry
        EntityRendererRegistry.register(ModEntities.GOOP, GoopRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseRenderer::new);
    }
}
