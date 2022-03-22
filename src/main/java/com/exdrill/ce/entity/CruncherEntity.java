package com.exdrill.ce.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CruncherEntity extends PathAwareEntity implements IAnimatable {
    private static final Ingredient TEMPTING_ITEMS;
    public CruncherEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "idleController", 0, this::isWalking));
    }

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return super.canBeLeashedBy(player);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, TEMPTING_ITEMS, false));
    }

    private <E extends IAnimatable> PlayState isWalking(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruncher.walk", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }


    public static DefaultAttributeContainer.Builder createCruncherAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15);
    }

    static {
        TEMPTING_ITEMS = Ingredient.ofItems(new ItemConvertible[]{Items.GLOW_BERRIES});
    }
}
