package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.client.model.entity.model.CruncherModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CruncherRenderer extends GeoEntityRenderer<CruncherEntity> {
    public CruncherRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherModel());
    }

    @Override
    public RenderLayer getRenderType(CruncherEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
