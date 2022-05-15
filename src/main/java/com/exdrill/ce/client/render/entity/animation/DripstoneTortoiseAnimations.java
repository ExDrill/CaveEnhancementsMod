package com.exdrill.ce.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Animation.Builder;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.animation.Transformation.Interpolations;
import net.minecraft.client.render.entity.animation.Transformation.Targets;

@Environment(EnvType.CLIENT)
public class DripstoneTortoiseAnimations {

    public static final Animation STOMPING;

    static {
        STOMPING = Builder.create(0.52F)
                .addBoneAnimation("body",
                        new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 12.5F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("head",
                        new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(-25.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(20.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.48F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("leg0", new Transformation(Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                                new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 45.0F), Interpolations.field_37885),
                                new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .addBoneAnimation("leg1", new Transformation(Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                        new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 22.5F), Interpolations.field_37885),
                        new Keyframe(0.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885)))
                .build();
    }
}
