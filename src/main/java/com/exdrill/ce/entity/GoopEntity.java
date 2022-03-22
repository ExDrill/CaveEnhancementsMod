package com.exdrill.ce.entity;

import com.exdrill.ce.registry.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
public class GoopEntity extends MobEntity implements IAnimatable, CustomBucketable {
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(GoopEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> STICKING_UP = DataTracker.registerData(GoopEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ServerWorld world;

    public GoopEntity(EntityType<? extends GoopEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    // Geckolib
    @Override
    public void registerControllers(AnimationData animationData) {}

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    // Sounds
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    // Attributes
    public static DefaultAttributeContainer.Builder createGoopAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_ARMOR, 2);
    }

    // Mob Group
    public EntityGroup getGroup() {
        return EntityGroup.DEFAULT;
    }

    // Nbt Related Stuff
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(STICKING_UP, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        System.out.println("WRITING NBT");
        System.out.println(this.isStickingUp());

        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putBoolean("StickingUp", this.isStickingUp());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));

        System.out.println("READING NBT");
        System.out.println(nbt.getBoolean("StickingUp"));

        setStickingUp(nbt.getBoolean("StickingUp"));
    }

    public void copyDataToStack(ItemStack stack) {
        CustomBucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        CustomBucketable.copyDataFromNbt(this, nbt);

        if (nbt.contains("StickingUp")) {
            this.isStickingUp();
        }
    }

    public boolean isFromBucket() {
        return (Boolean)this.dataTracker.get(FROM_BUCKET);
    }
    public boolean isStickingUp() {
        return (Boolean)this.dataTracker.get(STICKING_UP);
    }

    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    public void setStickingUp(boolean stickingUp) {
        this.dataTracker.set(STICKING_UP, stickingUp);
    }

    // Components
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

    // Spawn Conditions
    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else if (world.getLightLevel(LightType.BLOCK, pos) > 0) {
            return false;
        } else {
            int i = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
            return i <= random.nextInt(8);
        }
    }
    public static boolean canSpawnInDark(EntityType<? extends MobEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random);
    }
    public static boolean canSpawnIgnoreLightLevel(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && canMobSpawn(type, world, spawnReason, pos, random);
    }

    // Bucket Components
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return (ActionResult)CustomBucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.GOOP_BUCKET);
    }

    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL;
    }

    // Despawn Components
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    // Initialize
    public EntityData initialize(ServerWorldAccess serverWorld, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        world = serverWorld.toServerWorld();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        double origY = y;
        BlockPos blockUpPos = new BlockPos(x, y + 1, z);
        if (spawnReason != SpawnReason.NATURAL) {
            System.out.println("ARTIFICIAL");

            if (serverWorld.getBlockState(blockUpPos).isSolidSurface(world, blockUpPos, this, Direction.DOWN)) {
                setStickingUp(true);
            }
        }
        if (spawnReason == SpawnReason.NATURAL) {
            System.out.println("NATURAL");
            if (!world.isClient()) {
                System.out.println(world.getTopY());
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


            setStickingUp(true);
        }
        if (spawnReason == SpawnReason.BUCKET) {
            System.out.println("BUCKET");
            return entityData;
        }
        return super.initialize(serverWorld, difficulty, spawnReason, entityData, entityNbt);
    }

    public void tickMovement() {
        if(!getEntityWorld().isClient()){
            if(isStickingUp()) {
                this.setVelocity(this.getVelocity().multiply(0D, 0D, 0D));

                if(world != null){
                    double x = this.getX();
                    double y = this.getY();
                    double z = this.getZ();

                    BlockPos blockUpPos = new BlockPos(x, y + 1, z);

                    if(!world.getBlockState(blockUpPos).isSolidSurface(world, blockUpPos, this, Direction.DOWN)){
                        setStickingUp(false);
                    }
                }else{
                    world = getServer().getWorld(getEntityWorld().getRegistryKey());
                }
            }
        }
        super.tickMovement();
    }

    public int dripCooldown = 6;

    public void mobTick() {
       dripCooldown--;

       if(dripCooldown <= 0){
           dripCooldown = 6;

           if(getRandom().nextInt(1, 100) == 1) {
               drip();
           }
       }

        super.mobTick();
    }

    public void drip(){
        if(world != null) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            BigGoopDripProjectile bigGoopDripEntity = new BigGoopDripProjectile(world, x, y, z);
            world.spawnEntity(bigGoopDripEntity);
        }
    }
}
