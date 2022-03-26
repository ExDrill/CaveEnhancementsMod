package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.client.model.entity.model.DripstonePikeModel;
import com.exdrill.ce.client.model.entity.model.DripstoneTortoiseModel;
import com.exdrill.ce.entity.DripstonePikeEntity;
import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DripstonePikeRenderer extends GeoEntityRenderer<DripstonePikeEntity> {
    public DripstonePikeRenderer(EntityRendererFactory.Context context) {
        super(context, new DripstonePikeModel());
    }
}
