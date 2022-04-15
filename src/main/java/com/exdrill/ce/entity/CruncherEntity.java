package com.exdrill.ce.entity;

import com.exdrill.ce.entity.ai.goal.EatBlockGoal;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CruncherEntity extends PathAwareEntity implements IAnimatable, IAnimationTickable {
    private static final TrackedData<Boolean> IS_EATING_BLOCK = DataTracker.registerData(CruncherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_SHEARED = DataTracker.registerData(CruncherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient TEMPTING_ITEMS;
    public long lastEatTick;
    public int eatingTicks = 0;
    public int eatingAnimation = 0;
    public CruncherEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }


    // NBT Data
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_EATING_BLOCK, false);
        this.dataTracker.startTracking(IS_SHEARED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("lastEatTick", this.lastEatTick);
        nbt.putInt("eatingTicks", this.eatingTicks);
        nbt.putBoolean("isEatingBlock", this.isEating());
        nbt.putBoolean("isSheared", this.hasBeenSheared());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lastEatTick = nbt.getLong("lastEatTick");
        this.eatingTicks = nbt.getInt("eatingTicks");
        isEatingBlock(nbt.getBoolean("isEatingBlock"));
        isSheared(nbt.getBoolean("isSheared"));
    }

    // Eating Block NBT
    public void isEatingBlock(boolean isEating) {
        this.dataTracker.set(IS_EATING_BLOCK, isEating);
    }

    public boolean isEating() {
        return this.dataTracker.get(IS_EATING_BLOCK);
    }

    // Sheared NBT
    public void isSheared(boolean isSheared) {
        this.dataTracker.set(IS_SHEARED, isSheared);
    }

    public boolean hasBeenSheared() {
        return this.dataTracker.get(IS_SHEARED);
    }


    // Ticking
    @Override
    public void tick() {
        if (this.eatingTicks > 0) {
            this.eatingTicks--;
        }
        if (this.isEating() && this.eatingAnimation < 9) {
            this.eatingAnimation = 10;
        }
        if (this.eatingAnimation >= 10 && this.eatingAnimation < 25) {
            this.eatingAnimation++;
        }
        if (this.eatingAnimation == 25) {
            this.eatingAnimation = 0;
            this.isEatingBlock(false);
        }
        super.tick();
    }

    @Override
    public int tickTimer() {
        return age;
    }

    @Override
    protected boolean shouldSwimInFluids() {
        return true;
    }

    // Goals
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
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
        animationData.addAnimationController(new AnimationController(this, "grazingController", 5, this::isGrazing));
        animationData.addAnimationController(new AnimationController(this, "shearedController", 0, this::isSheared));
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
        if (this.isEating()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruncher.grazing", true));
        } else {
            event.getController().clearAnimationCache();
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState isSheared(AnimationEvent<E> event) {
        if (this.hasBeenSheared()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cruncher.sheared", true));
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


    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.NATURAL) {
            System.out.println("Spawning cruncher");
        }
        return entityData;
    }

    // Interactions
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS) && !this.hasBeenSheared() && this.isAlive()) {
            this.isSheared(true);
            itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            this.emitGameEvent(GameEvent.SHEAR, player);
            return ActionResult.SUCCESS;
        }
        if (itemStack.isOf(Items.GLOW_BERRIES) && this.eatingTicks == 0) {
            if (!this.world.isClient) {
                this.eatingTicks = 1200;
                itemStack.decrement(1);
                this.emitGameEvent(GameEvent.MOB_INTERACT, player);
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
