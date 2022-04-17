package com.exdrill.ce.client.render.entity.feature;

import com.exdrill.ce.client.render.entity.model.CruncherEntityModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CruncherEntityFeatureRenderer extends FeatureRenderer<CruncherEntity, CruncherEntityModel<CruncherEntity>> {
    public CruncherEntityFeatureRenderer(FeatureRendererContext<CruncherEntity, CruncherEntityModel<CruncherEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CruncherEntity cruncherEntity, float f, float g, float h, float j, float k, float l) {
        matrixStack.push();
        float m;

        matrixStack.translate(this.getContextModel().head.pivotX / 10F, (this.getContextModel()).head.pivotY / 8.0F, ((this.getContextModel()).head.pivotZ / 10F));
        m = 0.0F;
        matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(m));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(k));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(l));
        matrixStack.translate(0.0D, 1.15D, -0.5D);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));

        ItemStack itemStack = cruncherEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(cruncherEntity, itemStack, Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}