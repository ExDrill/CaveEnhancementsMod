package com.exdrill.ce.client.render.block;

import com.exdrill.ce.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.ce.client.render.block.model.RoseQuartzChimesModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RoseQuartzChimesRenderer extends GeoBlockRenderer<RoseQuartzChimesBlockEntity> {
    public RoseQuartzChimesRenderer() {
        super(new RoseQuartzChimesModel());
    }

    @Override
    public RenderLayer getRenderType(RoseQuartzChimesBlockEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
