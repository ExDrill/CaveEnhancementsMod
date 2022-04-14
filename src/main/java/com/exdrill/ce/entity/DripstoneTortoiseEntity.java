package com.exdrill.ce.entity;

import com.exdrill.ce.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class DripstoneTortoiseEntity extends HostileEntity implements IAnimatable, Angerable {
    private static final TrackedData<Integer> ANGER = DataTracker.registerData(DripstoneTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> SHOULD_STOMP = DataTracker.registerData(DripstoneTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public int stompTimer;

    public int soothed;

    @Nullable
    private UUID angryAt;

    public DripstoneTortoiseEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 30;
    }

    @Override
    public boolean isAngryAt(PlayerEntity player) {
        return false;
    }

    //NBT
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(ANGER, 0);
        this.dataTracker.startTracking(SHOULD_STOMP, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("shouldStomp", getShouldStomp());

        writeAngerToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        setShouldStomp(nbt.getBoolean("shouldStomp"));

        readAngerFromNbt(this.world, nbt);
    }

    public void setShouldStomp(boolean shouldStomp){
        dataTracker.set(SHOULD_STOMP, shouldStomp);
    }

    public boolean getShouldStomp(){
        return dataTracker.get(SHOULD_STOMP);
    }

    //Animations
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::controller));
    }

    private <E extends IAnimatable> PlayState controller(AnimationEvent<E> event) {
        if(getShouldStomp()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dripstone_tortoise.stomp", false));

            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
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

    // Sounds

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TURTLE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TURTLE_AMBIENT_LAND;
    }

    //AI
    protected void initGoals() {
        this.targetSelector.add(1, (new DripstoneTortoiseRevengeGoal(this)).setGroupRevenge(new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.add(4, new SpikeAttackGoal(this, 1.5D, true));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.5D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(6, new RandomSpikeGoal());
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

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if(damageSource == DamageSource.STALAGMITE || damageSource == DamageSource.FALLING_STALACTITE || damageSource.isProjectile() || damageSource.getAttacker() instanceof DripstoneTortoiseEntity) return true;

        return super.isInvulnerableTo(damageSource);
    }

    public boolean shouldAngerAt(Object entity) {
        if (!this.canTarget((LivingEntity)entity)) {
            return false;
        } else {
            return ((LivingEntity) entity).getType() == EntityType.PLAYER && this.isUniversallyAngry(((LivingEntity) entity).world) || ((LivingEntity) entity).getUuid().equals(this.getAngryAt());
        }
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
        return this.dataTracker.get(ANGER);
    }

    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER, angerTime);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(400);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    public void sooth(){
        soothed = 2;
        setAngryAt(null);
        setAngerTime(0);
    }

    //Tick
    @Override
    protected void mobTick() {
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, false);

            stompTimer--;

            if(stompTimer <= 0){
                setShouldStomp(false);
            }

            soothed--;
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

    public void summonPike(Vec3d pos){
        float y = (float) pos.getY();

        BlockPos blockDownPos = new BlockPos(pos.getX(), y - 1, pos.getZ());

        while(y < world.getTopY() && !world.getBlockState(blockDownPos).isSolidSurface(world, blockDownPos, this, Direction.UP)){
            y -= 0.1F;

            blockDownPos = new BlockPos(blockDownPos.getX(), y, blockDownPos.getZ());
        }

        if(y >= world.getTopY()){
            y = (float) pos.getY();
        }

        DripstonePikeEntity spellPart = new DripstonePikeEntity(ModEntities.DRIPSTONE_PIKE, world);

        spellPart.setPos(pos.getX(), y, pos.getZ());

        spellPart.owner = this;

        world.spawnEntity(spellPart);
    }

    private class SpikeAttackGoal extends Goal {
        protected final PathAwareEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private long lastUpdateTime;

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 20;
        }

        protected void attack(LivingEntity target, double squaredDistance) {
            double d = getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.cooldown <= 0) {
                this.resetCooldown();
                Vec3d targetPos = target.getPos();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3d(random.nextFloat(-1.5F, 1.5F) + targetPos.getX(),  targetPos.getY(), random.nextFloat(-1.5F, 1.5F)  + targetPos.getZ()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                world.playSound(null, new BlockPos(getPos()), SoundEvents.BLOCK_DRIPSTONE_BLOCK_BREAK, SoundCategory.HOSTILE, 1F, 1F);
            }
        }

        public SpikeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            long l = this.mob.world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    }
                }
            }
        }

        public boolean shouldContinue() {
            if(soothed > 0) return false;

            LivingEntity livingEntity = this.mob.getTarget();

            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().startMovingAlong(this.path, this.speed);
            this.mob.setAttacking(true);
            this.updateCountdownTicks = 0;
            this.cooldown = 0;
        }

        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();

            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAttacking(false);
            this.mob.getNavigation().stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null) {
                this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.targetX = livingEntity.getX();
                    this.targetY = livingEntity.getY();
                    this.targetZ = livingEntity.getZ();
                    this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                    if (d > 1024.0D) {
                        this.updateCountdownTicks += 10;
                    } else if (d > 256.0D) {
                        this.updateCountdownTicks += 5;
                    }

                    if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                        this.updateCountdownTicks += 15;
                    }

                    this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
                }

                this.cooldown = Math.max(this.cooldown - 1, 0);
                this.attack(livingEntity, d);
            }
        }

        protected void resetCooldown() {
            this.cooldown = 20;
        }
    }

    private class RandomSpikeGoal extends Goal {
        private int cooldown;
        private long lastUpdateTime;

        protected void spike() {
            if (this.cooldown <= 0) {
                this.resetCooldown();
                Vec3d targetPos = getPos();

                for(int i = 0; i < 10; i++){
                    summonPike(new Vec3d(random.nextFloat(-1.5F, 1.5F) + targetPos.getX(),  targetPos.getY(), random.nextFloat(-1.5F, 1.5F)  + targetPos.getZ()));
                }

                setShouldStomp(true);

                stompTimer = 10;

                world.playSound(null, new BlockPos(getPos()), SoundEvents.BLOCK_DRIPSTONE_BLOCK_BREAK, SoundCategory.HOSTILE, 1F, 1F);
            }
        }

        public RandomSpikeGoal() {}

        public boolean canStart() {
            if(isAttacking()) return false;

            long l = world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;

                return true;
            }
        }

        public boolean shouldContinue() {
            return !isAttacking();
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            this.cooldown = Math.max(this.cooldown - 1, 0);

            this.spike();
        }

        protected void resetCooldown() {
            this.cooldown = 400;
        }

        // Spawning Conditions

        public static boolean canSpawn(EntityType<? extends LivingEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
            return world.getBlockState(pos.down()).isIn(BlockTags.AXOLOTLS_SPAWNABLE_ON);
        }
    }
}
