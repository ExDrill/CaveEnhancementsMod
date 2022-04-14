package com.exdrill.ce.client.model.entity.model;

import com.exdrill.ce.entity.GoopEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setLivingAnimations(GoopEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone goop = this.getAnimationProcessor().getBone("goop");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (entity.isStickingUp()) {
            goop.setRotationX(267.05F);
            goop.setPositionY(-1F);
        }

        if (entity.isInvisible()) {
            goop.setScaleX(0F);
            goop.setScaleY(0F);
            goop.setScaleZ(0F);
        }
    }
}
