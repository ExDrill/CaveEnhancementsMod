package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class GoopTrapBlockEntity extends BlockEntity {
    public GoopTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GOOP_TRAP_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GoopTrapBlockEntity be) {
        Box box = new Box(pos);//.expand(1);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, (e) -> {return true;});

        LivingEntity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (LivingEntity)var2.next();

            otherEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 6, true, true));
        }
    }
}
