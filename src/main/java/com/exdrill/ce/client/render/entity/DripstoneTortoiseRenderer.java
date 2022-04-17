package com.exdrill.ce.client.render.entity;

import com.exdrill.ce.client.render.entity.model.DripstoneTortoiseModel;
import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DripstoneTortoiseRenderer extends GeoEntityRenderer<DripstoneTortoiseEntity> {
    public DripstoneTortoiseRenderer(EntityRendererFactory.Context context) {
        super(context, new DripstoneTortoiseModel());
    }
}
