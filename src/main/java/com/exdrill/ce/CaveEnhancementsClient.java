package com.exdrill.ce;

import com.exdrill.ce.client.render.block.RoseQuartzChimesRenderer;
import com.exdrill.ce.client.render.entity.CruncherEntityRenderer;
import com.exdrill.ce.client.render.entity.DripstonePikeEntityRenderer;
import com.exdrill.ce.client.render.entity.DripstoneTortoiseEntityRenderer;
import com.exdrill.ce.client.render.entity.GoopEntityRenderer;
import com.exdrill.ce.client.render.entity.model.CruncherEntityModel;
import com.exdrill.ce.client.render.entity.model.DripstonePikeEntityModel;
import com.exdrill.ce.client.render.entity.model.DripstoneTortoiseEntityModel;
import com.exdrill.ce.client.render.entity.model.GoopEntityModel;
import com.exdrill.ce.entity.EntitySpawnPacket;
import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModEntities;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {

    public static final Identifier PacketID = new Identifier(CaveEnhancements.NAMESPACE, "spawn_packet");

    @Override
    public void onInitializeClient() {
        ModBlocks.TransparentBlocks();
        ModParticles.registerClientParticles();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.GOOP, GoopEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);


        // Render Layers
        EntityModelLayerRegistry.registerModelLayer(CruncherEntityModel.ENTITY_MODEL_LAYER, CruncherEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoopEntityModel.ENTITY_MODEL_LAYER, GoopEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstoneTortoiseEntityModel.LAYER_LOCATION, DripstoneTortoiseEntityModel::texturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstonePikeEntityModel.LAYER_LOCATION, DripstonePikeEntityModel::getTexturedModelData);

        BlockEntityRendererRegistry.register(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY,
                (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RoseQuartzChimesRenderer());


        receiveEntityPacket();
    }

    //For spawning projectiles
    public void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID, ((client, handler, buf, responseSender) -> {
            EntityType<?> entityType = Registry.ENTITY_TYPE.get(buf.readVarInt());
            UUID uuid = buf.readUuid();
            int entityId = buf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(buf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            client.executeTask(() -> {
               if (MinecraftClient.getInstance().world == null)
                   throw new IllegalStateException("Cannot spawn entity without world!");

               Entity entity = entityType.create(MinecraftClient.getInstance().world);
               if (entity == null)
                   throw new IllegalStateException("Cannot create entity of type " + entityType);

               entity.updateTrackedPosition(pos.x, pos.y, pos.z);
               entity.setPos(pos.x, pos.y, pos.z);
               entity.setYaw(yaw);
               entity.setPitch(pitch);
               entity.setId(entityId);
               entity.setUuid(uuid);
               MinecraftClient.getInstance().world.addEntity(entityId, entity);
            });
        }));
    }
}
