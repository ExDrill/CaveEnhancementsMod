package com.exdrill.ce.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DripstonePikeEntity extends LivingEntity implements IAnimatable {
    public int dieTimer = 20;

    public int damageDelay = 4;

    public boolean didDamage = false;

    public LivingEntity owner;

    private static final TrackedData<Boolean> INVULNERABLE = DataTracker.registerData(DripstonePikeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public DripstonePikeEntity(EntityType<? extends DripstonePikeEntity> entityType, World world) {
        super(entityType, world);

        noClip = true;
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(INVULNERABLE, true);
    }

    //Animations
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::base));
    }

    private <E extends IAnimatable> PlayState base(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dripstone_pike.general"));
        return PlayState.CONTINUE;
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource != DamageSource.OUT_OF_WORLD;
    }

    public boolean isInvulnerable() {
        return this.dataTracker.get(INVULNERABLE);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<ItemStack>() {};
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.DIAMOND, 0);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public static DefaultAttributeContainer.Builder createDripstonePikeAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient()) {
            damageDelay--;

            if(!didDamage && damageDelay <= 0){
                didDamage = true;

                Box box = new Box(new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ())).expand(.5);

                List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> LivingEntity.class.isAssignableFrom(e.getClass()));

                Entity otherEntity;

                for(Iterator var2 = list.iterator(); var2.hasNext();) {
                    otherEntity = (Entity)var2.next();

                    otherEntity.damage(DamageSource.mobProjectile(this, owner), 8);
                }
            }

            dieTimer--;

            if (dieTimer <= 0) {
                discard();
            }
        }
    }
}
