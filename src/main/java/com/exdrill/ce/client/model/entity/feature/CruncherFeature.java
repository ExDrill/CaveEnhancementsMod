package com.exdrill.ce.client.model.entity.feature;

import com.exdrill.ce.Main;
import com.exdrill.ce.client.model.entity.CruncherRenderer;
import com.exdrill.ce.client.model.entity.model.GoopModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class CruncherFeature extends GeoLayerRenderer<CruncherEntity> {
    private static final RenderLayer SKIN = RenderLayer.getEntityAlpha(new Identifier(Main.NAMESPACE, "textures/entity/cruncher/moss_hat.png"));

    private final IGeoRenderer<CruncherEntity> renderer;
    public CruncherFeature(IGeoRenderer<CruncherEntity> entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    public boolean isSheared(CruncherEntity entity) {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, CruncherEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferIn.getBuffer(SKIN);
        if (isSheared(entitylivingbaseIn)) {
            renderer.render(
                    getEntityModel().getModel(getEntityModel().getModelLocation(entitylivingbaseIn)),
                    entitylivingbaseIn,
                    partialTicks,
                    SKIN,
                    matrixStackIn,
                    bufferIn,
                    vertexConsumer,
                    0,
                    OverlayTexture.DEFAULT_UV,
                    1.0F, 1.0F, 1.0F, 1.0F
            );
        }
    }
}
