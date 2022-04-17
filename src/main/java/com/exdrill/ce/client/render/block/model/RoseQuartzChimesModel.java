package com.exdrill.ce.client.render.block.model;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.block.entity.RoseQuartzChimesBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoseQuartzChimesModel extends AnimatedGeoModel<RoseQuartzChimesBlockEntity> {
    @Override
    public Identifier getAnimationFileLocation(RoseQuartzChimesBlockEntity entity) {
        return new Identifier(CaveEnhancements.NAMESPACE, "animations/rose_quartz_chimes.animation.json");
    }

    @Override
    public Identifier getModelLocation(RoseQuartzChimesBlockEntity animatable) {
        return new Identifier(CaveEnhancements.NAMESPACE, "geo/rose_quartz_chimes.geo.json");
    }

    @Override
    public Identifier getTextureLocation(RoseQuartzChimesBlockEntity entity) {
        return new Identifier(CaveEnhancements.NAMESPACE, "textures/entity/rose_quartz_chimes.png");
    }
}
