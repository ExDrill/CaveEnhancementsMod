package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, pos, state);
    }
}
