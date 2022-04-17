package com.exdrill.ce.client.render.entity;

import com.exdrill.ce.client.render.entity.model.GoopModel;
import com.exdrill.ce.entity.GoopEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GoopRenderer extends GeoEntityRenderer<GoopEntity> {
    public GoopRenderer(EntityRendererFactory.Context context) {
        super(context, new GoopModel());
    }
}
