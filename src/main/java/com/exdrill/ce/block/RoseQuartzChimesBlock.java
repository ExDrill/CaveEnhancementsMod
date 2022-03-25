package com.exdrill.ce.block;

import com.exdrill.ce.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.ce.registry.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoseQuartzChimesBlock extends BlockWithEntity {

    public RoseQuartzChimesBlock (FabricBlockSettings settings){
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RoseQuartzChimesBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.ROSE_QUARTZ_CHIMES_BLOCK_ENTITY, RoseQuartzChimesBlockEntity::tick);
    }
}
