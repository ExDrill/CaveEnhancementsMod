package com.exdrill.ce.client.model.entity.model;

import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CruncherModel extends AnimatedTickingGeoModel<CruncherEntity> {
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setLivingAnimations(CruncherEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        IBone upperJaw = this.getAnimationProcessor().getBone("upper_jaw");
        IBone lowerJaw = this.getAnimationProcessor().getBone("lower_jaw");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (!entity.isEatingBlock) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
        }
     }

}
