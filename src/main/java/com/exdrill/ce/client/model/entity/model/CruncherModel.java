package com.exdrill.ce.client.model.entity.model;

import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CruncherModel extends AnimatedGeoModel<CruncherEntity> {
    @Override
    public Identifier getModelLocation(CruncherEntity object)
    {
        return new Identifier("ce", "geo/cruncher.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CruncherEntity object)
    {
        return new Identifier("ce", "textures/entity/cruncher/cruncher.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CruncherEntity object) {
        return new Identifier("ce", "animations/cruncher.animation.json");
    }
}
