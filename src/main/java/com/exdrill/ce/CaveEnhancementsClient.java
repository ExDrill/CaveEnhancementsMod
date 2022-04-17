package com.exdrill.ce;

import com.eliotlash.mclib.math.functions.classic.Mod;
import com.exdrill.ce.client.render.block.RoseQuartzChimesRenderer;
import com.exdrill.ce.client.render.entity.CruncherEntityRenderer;
import com.exdrill.ce.client.render.entity.DripstonePikeRenderer;
import com.exdrill.ce.client.render.entity.DripstoneTortoiseRenderer;
import com.exdrill.ce.client.render.entity.GoopRenderer;
import com.exdrill.ce.client.render.entity.model.CruncherEntityModel;
import com.exdrill.ce.entity.EntitySpawnPacket;
import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModEntities;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModBlocks.TransparentBlocks();
        ModParticles.registerClientParticles();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.GOOP, GoopRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);

        // Render Layers
        EntityModelLayerRegistry.registerModelLayer(CruncherEntityModel.ENTITY_MODEL_LAYER, CruncherEntityModel::getTexturedModelData);


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
