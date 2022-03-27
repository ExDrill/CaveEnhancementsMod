package com.exdrill.ce;

import com.exdrill.ce.client.model.block.RoseQuartzChimesRenderer;
import com.exdrill.ce.client.model.entity.CruncherRenderer;
import com.exdrill.ce.client.model.entity.DripstonePikeRenderer;
import com.exdrill.ce.client.model.entity.DripstoneTortoiseRenderer;
import com.exdrill.ce.client.model.entity.GoopRenderer;
import com.exdrill.ce.entity.EntitySpawnPacket;
import com.exdrill.ce.particle.RoseQuartzAura;
import com.exdrill.ce.particle.Shockwave;
import com.exdrill.ce.particle.SmallGoopDrip;
import com.exdrill.ce.particle.SoothingNote;
import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModEntities;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import software.bernie.example.client.renderer.tile.BotariumTileRenderer;
import software.bernie.example.registry.TileRegistry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.TransparentBlocks();
        ModParticles.registerClientParticles();

        // Entity Renderer Registry
        EntityRendererRegistry.register(ModEntities.GOOP, GoopRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);

        BlockEntityRendererRegistry.register(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY,
                (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RoseQuartzChimesRenderer());

        receiveEntityPacket();
    }

    //For spawning projectiles
    public static final Identifier PacketID = new Identifier(CaveEnhancements.NAMESPACE, "spawn_packet");

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
