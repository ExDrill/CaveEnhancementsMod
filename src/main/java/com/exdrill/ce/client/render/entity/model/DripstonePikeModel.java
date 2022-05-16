package com.exdrill.ce.client.render.entity.model;

import com.exdrill.ce.entity.DripstonePikeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DripstonePikeModel extends AnimatedGeoModel<DripstonePikeEntity> {
    @Override
    public Identifier getModelResource(DripstonePikeEntity object)
    {
        return new Identifier("ce", "geo/dripstone_pike.geo.json");
    }

    @Override
    public Identifier getTextureResource(DripstonePikeEntity object)
    {
        return new Identifier("ce", "textures/entity/dripstone_tortoise/dripstone_pike.png");
    }

    @Override
    public Identifier getAnimationResource(DripstonePikeEntity object) {
        return new Identifier("ce", "animations/dripstone_pike.animation.json");
    }
}
