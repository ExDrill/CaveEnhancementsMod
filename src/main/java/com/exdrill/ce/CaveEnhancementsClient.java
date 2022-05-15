package com.exdrill.ce;

import com.exdrill.ce.client.render.block.RoseQuartzChimesRenderer;
import com.exdrill.ce.client.render.entity.CruncherEntityRenderer;
import com.exdrill.ce.client.render.entity.DripstonePikeRenderer;
import com.exdrill.ce.client.render.entity.DripstoneTortoiseEntityRenderer;
import com.exdrill.ce.client.render.entity.GoopEntityRenderer;
import com.exdrill.ce.client.render.entity.model.CruncherEntityModel;
import com.exdrill.ce.client.render.entity.model.DripstoneTortoiseEntityModel;
import com.exdrill.ce.client.render.entity.model.GoopEntityModel;
import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModEntities;
import com.exdrill.ce.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class CaveEnhancementsClient implements ClientModInitializer {




    @Override
    public void onInitializeClient() {
        ModBlocks.TransparentBlocks();
        ModParticles.registerClientParticles();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.GOOP, GoopEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER, CruncherEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_TORTOISE, DripstoneTortoiseEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_PIKE, DripstonePikeRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIG_GOOP_DRIP_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);


        // Render Layers
        EntityModelLayerRegistry.registerModelLayer(CruncherEntityModel.ENTITY_MODEL_LAYER, CruncherEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoopEntityModel.ENTITY_MODEL_LAYER, GoopEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DripstoneTortoiseEntityModel.LAYER_LOCATION, DripstoneTortoiseEntityModel::texturedModelData);

        BlockEntityRendererRegistry.register(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY,
                (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RoseQuartzChimesRenderer());


        //receiveEntityPacket();
    }

    //For spawning projectiles
    public void receiveEntityPacket() {
//import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
        /*
        ClientSidePacketRegistry.INSTANCE.register(CaveEnhancements.PacketID, (ctx, byteBuf) -> {
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
        */
    }
}
