package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.awt.desktop.SystemEventListener;
import java.util.Iterator;
import java.util.List;

public class RoseQuartzChimesBlockEntity extends BlockEntity  {
    public static int ticksTillActivateClear = 600;


    public RoseQuartzChimesBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RoseQuartzChimesBlockEntity entity) {
        if (ticksTillActivateClear <= 300 && world.isRaining()) {
            ticksTillActivateClear = 600;
        }

        if (ticksTillActivateClear <= 0 && !world.isRaining()) {
            ticksTillActivateClear = 600;
        }

        if(ticksTillActivateClear > 0) {
            ticksTillActivateClear--;
            //System.out.println(ticksTillActivateClear);
        }

        Box box = new Box(pos).expand(8);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, (e) -> true);

        LivingEntity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (LivingEntity)var2.next();

            if (!LivingEntity.class.isAssignableFrom(otherEntity.getClass()) || PlayerEntity.class.isAssignableFrom(otherEntity.getClass())) continue;

            if (world.isRaining() && ticksTillActivateClear <= 300) {
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            else if (!world.isRaining() && ticksTillActivateClear <= 0) {
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            if(HostileEntity.class.isAssignableFrom(otherEntity.getClass())) {
                if(world.isRaining() && ticksTillActivateClear <= 300){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2, true, true));
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0, true, true));
                    System.out.println("Hostile Applied II");
                }else if(!world.isRaining() && ticksTillActivateClear <= 0 ){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 150, 2, true, true));
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0, true, true));
                    System.out.println("Hostile Applied I");
                }
            }
            else{
                if(world.isRaining() && ticksTillActivateClear <= 300){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 3, true, true));
                    System.out.println("Non Hostile Applied");
                }else if(!world.isRaining() && ticksTillActivateClear <= 0 ){
                    otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 2, true, true));
                    System.out.println("Non Hostile Applied");
                }
            }
        }
    }
}
