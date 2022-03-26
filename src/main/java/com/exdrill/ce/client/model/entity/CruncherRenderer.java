package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.ce.client.model.entity.feature.CruncherFeature;
import com.exdrill.ce.client.model.entity.model.CruncherModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CruncherRenderer extends GeoEntityRenderer<CruncherEntity> {
    public CruncherRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherModel());
        this.addLayer(new CruncherFeature(this));
    }



    @Override
    public RenderLayer getRenderType(CruncherEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
