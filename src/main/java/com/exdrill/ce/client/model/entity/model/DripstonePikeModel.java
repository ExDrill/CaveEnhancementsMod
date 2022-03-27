package com.exdrill.ce.client.model.entity.model;

import com.exdrill.ce.entity.DripstonePikeEntity;
import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DripstonePikeModel extends AnimatedGeoModel<DripstonePikeEntity> {
    @Override
    public Identifier getModelLocation(DripstonePikeEntity object)
    {
        return new Identifier("ce", "geo/dripstone_pike.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DripstonePikeEntity object)
    {
        return new Identifier("ce", "textures/entity/dripstone_tortoise/dripstone_pike.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DripstonePikeEntity object) {
        return new Identifier("ce", "animations/dripstone_pike.animation.json");
    }
}
