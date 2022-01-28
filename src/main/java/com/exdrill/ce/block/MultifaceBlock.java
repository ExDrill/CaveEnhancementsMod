package com.exdrill.ce.block;

import java.util.function.ToIntFunction;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MultifaceBlock extends AbstractLichenBlock {
    public MultifaceBlock(Settings settings) {
        super(settings);
    }
    public static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
        return (state) -> {
            return AbstractLichenBlock.hasAnyDirection(state) ? luminance : 0;
        };
    }
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }

}
