package com.exdrill.ce.block;

import com.exdrill.ce.block.entity.SpectacleCandleBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpectacleCandleBlock extends CandleBlock implements BlockEntityProvider  {
    public SpectacleCandleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpectacleCandleBlockEntity(pos, state);
    }
}