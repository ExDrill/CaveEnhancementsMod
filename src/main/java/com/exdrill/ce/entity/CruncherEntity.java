package com.exdrill.ce.entity;

import com.exdrill.ce.entity.ai.goal.EatBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CruncherEntity extends PathAwareEntity implements IAnimatable, IAnimationTickable {
    private static final Ingredient TEMPTING_ITEMS;
    public long lastEatTick;
    public int eatingTicks = 0;
    public boolean isEatingBlock;
    public int eatingAnimation = 0;
    public CruncherEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    // NBT Data
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("lastEatTick", this.lastEatTick);
        nbt.putInt("eatingTicks", this.eatingTicks);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lastEatTick = nbt.getLong("lastEatTick");
        this.eatingTicks = nbt.getInt("eatingTicks");
    }

    // Ticking
    @Override
    public void tick() {
        if (this.eatingTicks > 0) {
            this.eatingTicks--;
        }
        System.out.println(this.isEatingBlock);


        super.tick();
    }

    @Override
    public int tickTimer() {
        return age;
    }

    // Goals
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(0, new EatBlockGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, TEMPTING_ITEMS, false));
    }


    // Animations
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "idleController", 0, this::isWalking));
        animationData.addAnimationController(new AnimationController(this, "idleController", 0, this::isGrazing));
    }

    private <E extends IAnimatable> PlayState isWalking(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruncher.walk", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable> PlayState isGrazing(AnimationEvent<E> event) {
        if (this.isEatingBlock) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruncher.grazing", true));
        } else {
            event.getController().clearAnimationCache();
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    // Interactions
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.GLOW_BERRIES) && this.eatingTicks == 0) {
            if (!this.world.isClient) {
                this.eatingTicks = 1200;
                itemStack.decrement(1);

                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }


    // Leash
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, 0.6F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return super.canBeLeashedBy(player);
    }

    // Attributes
    public static DefaultAttributeContainer.Builder createCruncherAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15);
    }

    static {
        TEMPTING_ITEMS = Ingredient.ofItems(Items.GLOW_BERRIES);
    }
}
