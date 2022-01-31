package com.exdrill.ce.block.entity;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.jetbrains.annotations.Nullable;

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SPECTACLE_CANDLE_BLOCK_ENTITY, pos, state);
    }
}
