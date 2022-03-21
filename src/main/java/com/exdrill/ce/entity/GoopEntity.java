package com.exdrill.ce.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class GoopEntity extends MobEntity implements IAnimatable {
    public ServerWorld world;

    public boolean stickingUp;

    public GoopEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    @Override
    public void registerControllers(AnimationData animationData) {}

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    public static DefaultAttributeContainer.Builder createGoopAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_ARMOR, 2);
    }

    protected boolean isDisallowedInPeaceful() {
        return true;
    }

    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public void pushAwayFrom(Entity entity) {
    }

    public EntityData initialize(ServerWorldAccess serverWorld, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        world = serverWorld.toServerWorld();

        if (!world.isClient()) {
            System.out.println(world.getTopY());

            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            double origY = y;

            BlockPos blockUpPos = new BlockPos(x, y + 1, z);

            while(y < world.getTopY() && !serverWorld.getBlockState(blockUpPos).isSolidSurface(world, blockUpPos, this, Direction.DOWN)){
                x = this.getX();
                y = this.getY();
                z = this.getZ();

                teleport(x, y + 0.1D, z);

                y = this.getY();

                blockUpPos = new BlockPos(x, y + 1, z);
            }

            if(y >= world.getTopY()){
                y = origY;
                teleport(x, y + 0.1D, z);
            }

            System.out.println(y);
        }

        stickingUp = true;

        if (spawnReason == SpawnReason.COMMAND || spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.DISPENSER) {
            System.out.println("ART");
        }

        if (spawnReason == SpawnReason.NATURAL) {
            System.out.println("NAT");
        }

        return super.initialize(serverWorld, difficulty, spawnReason, entityData, entityNbt);
    }

    public void tick() {
        super.tick();
    }

    public void tickMovement() {
        if(stickingUp) {
            this.setVelocity(this.getVelocity().multiply(0D, 0D, 0D));

            if(world != null){
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();

                BlockPos blockUpPos = new BlockPos(x, y + 1, z);

                if(!world.getBlockState(blockUpPos).isSolidSurface(world, blockUpPos, this, Direction.DOWN)){
                    stickingUp = false;
                }
            }
        }

        super.tickMovement();
    }
}
