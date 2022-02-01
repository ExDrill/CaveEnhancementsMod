package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ChargedLightningAnchorBlockEntity extends BlockEntity {
    public ChargedLightningAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CHARGED_LIGHTNING_ANCHOR_BLOCK_ENTITY, pos, state);
    }
}
