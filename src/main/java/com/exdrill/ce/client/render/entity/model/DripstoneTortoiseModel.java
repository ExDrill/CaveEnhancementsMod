package com.exdrill.ce.client.render.entity.model;

import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DripstoneTortoiseModel extends AnimatedGeoModel<DripstoneTortoiseEntity> {
    @Override
    public Identifier getModelResource(DripstoneTortoiseEntity object)
    {
        return new Identifier("ce", "geo/dripstone_tortoise.geo.json");
    }

    @Override
    public Identifier getTextureResource(DripstoneTortoiseEntity object)
    {
        return new Identifier("ce", "textures/entity/dripstone_tortoise/dripstone_tortoise.png");
    }

    @Override
    public Identifier getAnimationResource(DripstoneTortoiseEntity object) {
        return new Identifier("ce", "animations/dripstone_tortoise.animation.json");
    }
}
