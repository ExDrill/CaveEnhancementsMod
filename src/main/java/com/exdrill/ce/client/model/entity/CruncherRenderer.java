package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.client.model.entity.model.CruncherModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CruncherRenderer extends GeoEntityRenderer<CruncherEntity> {
    public CruncherRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherModel());
    }
}
