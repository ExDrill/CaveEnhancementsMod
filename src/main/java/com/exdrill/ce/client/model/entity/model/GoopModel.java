package com.exdrill.ce.client.model.entity.model;

import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import com.exdrill.ce.entity.GoopEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoopModel extends AnimatedGeoModel<GoopEntity>
{
    @Override
    public Identifier getModelLocation(GoopEntity object)
    {
        return new Identifier("ce", "geo/goop.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GoopEntity object)
    {
        return new Identifier("ce", "textures/entity/goop.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GoopEntity animatable) {
        return new Identifier("ce", "animations/goop.animation.json");
    }
}
