package com.exdrill.ce.block.entity.renderer;

import com.exdrill.ce.block.entity.LightningAnchorBlockEntity;
import com.exdrill.ce.registry.ModItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class LightningAnchorBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public LightningAnchorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.5, -0.5, 0.5);

        ((LightningAnchorBlockEntity)entity).scale -= tickDelta * 0.25;

        if(((LightningAnchorBlockEntity)entity).scale < 0){
            ((LightningAnchorBlockEntity)entity).scale = 0;
        }

        float actualScale = (1 - ((LightningAnchorBlockEntity)entity).scale) * 10 + 1;

        if(((LightningAnchorBlockEntity)entity).scale == 0){
            actualScale = 0;
        }

        matrices.scale(actualScale, actualScale, actualScale);

        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.LIGHTNING_ANCHOR), ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

        matrices.pop();
    }
}
