package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.client.model.entity.model.CruncherModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class CruncherRenderer extends ExtendedGeoEntityRenderer<CruncherEntity> {
    public CruncherRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherModel());
    }

    @Override
    public RenderLayer getRenderType(CruncherEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Nullable
    @Override
    protected Identifier getTextureForBone(String boneName, CruncherEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, CruncherEntity currentEntity) {
        ItemStack itemStack = currentEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        Item item2 = itemStack.getItem();
        return boneName.equals("Item") ? new ItemStack(item2) : null;
    }

    @Override
    protected ModelTransformation.Mode getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return ModelTransformation.Mode.NONE;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, CruncherEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CruncherEntity currentEntity, IBone bone) {


        ItemStack itemStack = currentEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        Item item2 = itemStack.getItem();

        if (!itemStack.isEmpty()) {
            matrixStack.scale(0.55F, 0.55F, 0.55F);
        }  else {
            matrixStack.scale(0.0F, 0.0F, 0.0F);
        }

    }

    @Override
    protected void preRenderBlock(BlockState block, String boneName, CruncherEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CruncherEntity currentEntity, IBone bone) {
    }

    @Override
    protected void postRenderBlock(BlockState block, String boneName, CruncherEntity currentEntity) {

    }
}
