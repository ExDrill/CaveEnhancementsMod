package com.exdrill.ce.client.render.entity;

import com.exdrill.ce.client.render.entity.model.DripstonePikeModel;
import com.exdrill.ce.entity.DripstonePikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DripstonePikeRenderer extends GeoEntityRenderer<DripstonePikeEntity> {
    public DripstonePikeRenderer(EntityRendererFactory.Context context) {
        super(context, new DripstonePikeModel());
    }
}
