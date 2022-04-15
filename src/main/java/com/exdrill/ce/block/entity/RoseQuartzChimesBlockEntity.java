package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import com.exdrill.ce.registry.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;
import java.util.List;

public class RoseQuartzChimesBlockEntity extends BlockEntity implements IAnimatable {
    public int ticksTillActivateClear = 600;


    public RoseQuartzChimesBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RoseQuartzChimesBlockEntity entity) {
        if(entity.ticksTillActivateClear > 0) {
            entity.ticksTillActivateClear--;
        }

        Box box = new Box(pos).expand(8);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, (e) -> true);

        LivingEntity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (LivingEntity)var2.next();

            if (!LivingEntity.class.isAssignableFrom(otherEntity.getClass()) || PlayerEntity.class.isAssignableFrom(otherEntity.getClass())) continue;

            if(HostileEntity.class.isAssignableFrom(otherEntity.getClass())) {
                if(world.isRaining() && entity.ticksTillActivateClear <= 300){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2, true, true));
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0, true, true));
                }else if(!world.isRaining() && entity.ticksTillActivateClear <= 0 ){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 150, 2, true, true));
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0, true, true));
                }
            }
            else{
                if(world.isRaining() && entity.ticksTillActivateClear <= 300){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 1, true, true));
                }else if(!world.isRaining() && entity.ticksTillActivateClear <= 0 ){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 0, true, true));
                }
            }
        }

        if ((entity.ticksTillActivateClear <= 300 && world.isRaining()) || (entity.ticksTillActivateClear <= 0 && !world.isRaining())) {
            entity.ticksTillActivateClear = 600;
            world.addParticle(ModParticles.ROSE_CHIMES, entity.getPos().getX() + 0.5D, entity.getPos().getY() + 0.3D, entity.getPos().getZ() + 0.5D, 0D, 0D, 0D);
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "chimes_setup", 100, this::animationClear));
        animationData.addAnimationController(new AnimationController(this, "rain", 100, this::animationRain));
        animationData.addAnimationController(new AnimationController(this, "none", 0, this::none));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState animationClear(AnimationEvent<E> event) {
        assert this.world != null;
        if (!world.isRaining()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rose_quartz_chimes.clear", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable> PlayState animationRain(AnimationEvent<E> event) {
        assert this.world != null;
        if (world.isRaining()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rose_quartz_chimes.rain", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }


    private <E extends IAnimatable> PlayState none(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rose_quartz_chimes.render", true));
        return PlayState.CONTINUE;
    }
}
