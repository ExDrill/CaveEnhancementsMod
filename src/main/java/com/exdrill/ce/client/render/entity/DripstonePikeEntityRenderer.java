package com.exdrill.ce.client.render.entity;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.client.render.entity.model.DripstonePikeEntityModel;
import com.exdrill.ce.entity.DripstonePikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DripstonePikeEntityRenderer extends MobEntityRenderer<DripstonePikeEntity, DripstonePikeEntityModel<DripstonePikeEntity>> {

    public static final Identifier TEXTURE = new Identifier(CaveEnhancements.NAMESPACE, "textures/entity/dripstone_tortoise/dripstone_pike.png");

    public DripstonePikeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DripstonePikeEntityModel<>(context.getPart(DripstonePikeEntityModel.LAYER_LOCATION)), 0.0f);
    }



    @Override
    public Identifier getTexture(DripstonePikeEntity entity) {
        return TEXTURE;
    }
}
