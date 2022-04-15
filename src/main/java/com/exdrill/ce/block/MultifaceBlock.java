package com.exdrill.ce.block;

import net.minecraft.block.AbstractLichenBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MultifaceBlock extends AbstractLichenBlock {
    public MultifaceBlock(Settings settings) {
        super(settings);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }

}
