package com.exdrill.ce.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class DripstoneTortoiseEntity extends PathAwareEntity implements IAnimatable, Angerable {
    private static final TrackedData<Integer> ANGER = DataTracker.registerData(DripstoneTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private UUID angryAt;

    public DripstoneTortoiseEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 30;
    }

    //NBT
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        this.writeAngerToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.readAngerFromNbt(this.world, nbt);
    }

    //Animations
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::isWalking));
    }

    private <E extends IAnimatable> PlayState isWalking(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dripstone_tortoise.walk", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void initGoals() {
        //this.targetSelector.add(3, new UniversalAngerGoal(this, true));
        this.targetSelector.add(1, (new DripstoneTortoiseRevengeGoal(this)).setGroupRevenge(new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.5D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDripstoneTortoiseAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.125D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ARMOR, 5)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    public boolean shouldAngerAt(Object entity) {
        if (!this.canTarget((LivingEntity)entity)) {
            return false;
        } else {
            return ((LivingEntity)entity).getType() == EntityType.PLAYER && this.isUniversallyAngry(((LivingEntity)entity).world) ? true : ((LivingEntity)entity).getUuid().equals(this.getAngryAt());
        }
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    public boolean canSpawn(WorldView world) {
        return !world.containsFluid(this.getBoundingBox()) && world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    protected void pushOutOfBlocks(double x, double y, double z) {
        super.pushOutOfBlocks(x, y, z);
    }

    //Anger
    public int getAngerTime() {
        return (Integer)this.dataTracker.get(ANGER);
    }

    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER, angerTime);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(200);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        System.out.println("SET ANGRY AT");
        System.out.println(angryAt);
        this.angryAt = angryAt;
    }

    //Tick
    @Override
    protected void mobTick() {
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, false);
        }
    }

    //Goals
    private class DripstoneTortoiseRevengeGoal extends RevengeGoal {
        DripstoneTortoiseRevengeGoal(DripstoneTortoiseEntity dripstoneTortoise) {
            super(dripstoneTortoise, new Class[0]);
        }

        public boolean shouldContinue() {
            return hasAngerTime() && super.shouldContinue();
        }

        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof DripstoneTortoiseEntity && mob.canSee(target)) {
                mob.setTarget(target);
            }

        }
    }
}
