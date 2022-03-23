package com.exdrill.ce.client.renderer.item;

import com.exdrill.ce.client.model.item.AmethystFluteModel;
import com.exdrill.ce.item.AmethystFluteItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AmethystFluteRenderer extends GeoItemRenderer<AmethystFluteItem> {
    public AmethystFluteRenderer() {
        super(new AmethystFluteModel());
    }

    public AmethystFluteRenderer(AnimatedGeoModel<AmethystFluteItem> modelProvider) {
        super(modelProvider);
    }
}
