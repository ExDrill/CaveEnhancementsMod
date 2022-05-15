package com.exdrill.ce.entity;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DripstonePikeEntity extends Entity {
    public int dieTimer = 20;

    public int damageDelay = 4;

    public boolean didDamage = false;

    public LivingEntity owner;

    public boolean checkedSight =  false;

    public final AnimationState risingAnimationState = new AnimationState();

    public DripstonePikeEntity(EntityType<? extends DripstonePikeEntity> entityType, World world) {
        super(entityType, world);

        noClip = true;
    }

    @Override
    protected void initDataTracker() {

    }



    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }


    @Override
    public void tick() {
        super.tick();

        risingAnimationState.startIfNotRunning();

        if(!world.isClient()) {
            if(!checkedSight){
                checkedSight = true;

                if(owner != null && isInsideWall()){
                    discard();
                }
            }

            damageDelay--;

            if(!didDamage && damageDelay <= 0){
                didDamage = true;

                Box box = new Box(new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ())).expand(.1);

                List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> LivingEntity.class.isAssignableFrom(e.getClass()));

                Entity otherEntity;

                for (Entity entity : list) {
                    otherEntity = entity;

                    otherEntity.damage(DamageSource.mobProjectile(this, owner), 8);

                    if (otherEntity instanceof CreeperEntity) {
                        otherEntity.damage(DamageSource.mobProjectile(this, owner), 20);
                    }
                }
            }

            dieTimer--;

            if (dieTimer <= 0) {
                discard();
            }
        }
    }
}
