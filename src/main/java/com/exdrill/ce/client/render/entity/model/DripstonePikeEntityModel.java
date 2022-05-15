package com.exdrill.ce.client.render.entity.model;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.client.render.entity.animation.DripstoneTortoiseAnimations;
import com.exdrill.ce.entity.DripstonePikeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class DripstonePikeEntityModel<T extends Entity> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(CaveEnhancements.NAMESPACE, "dripstone_pike"), "main");

    private final ModelPart root;
    private final ModelPart pike;

    public DripstonePikeEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.pike = this.root.getChild("pike");
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData pike = root.addChild("pike", ModelPartBuilder.create()
                        .uv(0, -16).cuboid(0.0F, -48.0F, -8.0F, 0.0F, 48.0F, 16.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-8.0F, -48.0F, 0.0F, 16.0F, 48.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 48.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 16, 48);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
