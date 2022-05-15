package com.exdrill.ce.client.render.entity;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.client.render.entity.model.DripstonePikeEntityModel;
import com.exdrill.ce.entity.DripstonePikeEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DripstonePikeEntityRenderer extends EntityRenderer<DripstonePikeEntity> {

    private static final Identifier TEXTURE = new Identifier(CaveEnhancements.NAMESPACE, "textures/entity/dripstone_tortoise/dripstone_pike.png");
    private final DripstonePikeEntityModel<DripstonePikeEntity> model;


    public DripstonePikeEntityRenderer(Context context) {
        super(context);
        this.model = new DripstonePikeEntityModel<>(context.getPart(DripstonePikeEntityModel.LAYER_LOCATION));
    }


    @Override
    public void render(DripstonePikeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float g = 0;
        this.model.setAngles(entity, g, g, tickDelta, g, g);

        // make an animation that moves the entity up and down using MathHelper
        float y = MathHelper.sin(tickDelta * 0.1F) * 0.05F + 0.1F;
        matrices.push();
        matrices.translate(0, y, 0);

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(DripstonePikeEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(DripstonePikeEntity entity) {
        return TEXTURE;
    }

}
