package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LightningAnchorBlockEntity extends BlockEntity {
    public LightningAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LIGHTNING_ANCHOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LightningAnchorBlockEntity entity) {
        Box box = new Box(pos).expand(1.5);

        List<Entity> list = world.getEntitiesByClass(Entity.class, box, (e) -> {return true;});

        Entity otherEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext();) {
            otherEntity = (Entity)var2.next();
            if(otherEntity.getClass() == LightningEntity.class && !world.isReceivingRedstonePower(pos)){
                world.setBlockState(pos, ModBlocks.CHARGED_LIGHTNING_ANCHOR.getDefaultState());
            }
        }
    }
}
