package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Degradable;
import net.minecraft.block.Oxidizable;

import java.util.Optional;
import java.util.function.Supplier;

public interface OxidizableReceiver extends Degradable<Oxidizable.OxidationLevel> {


    Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(ModBlocks.REDSTONE_RECEIVER, ModBlocks.EXPOSED_REDSTONE_RECEIVER)
            .put(ModBlocks.EXPOSED_REDSTONE_RECEIVER, ModBlocks.WEATHERED_REDSTONE_RECEIVER)
            .put(ModBlocks.WEATHERED_REDSTONE_RECEIVER, ModBlocks.OXIDIZED_REDSTONE_RECEIVER)
            .build());

    Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> OXIDATION_LEVEL_INCREASES.get().inverse());


    static Optional<Block> getDecreasedOxidationBlock(Block block) {
        return Optional.ofNullable((Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block));
    }

    static Block getUnaffectedOxidationBlock(Block block) {
        Block block2 = block;

        for(Block block3 = (Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block); block3 != null; block3 = (Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block3)) {
            block2 = block3;
        }

        return block2;
    }

    static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
        return getDecreasedOxidationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
    }

    static Optional<Block> getIncreasedOxidationBlock(Block block) {
        return Optional.ofNullable((Block)((BiMap)OXIDATION_LEVEL_INCREASES.get()).get(block));
    }

    static BlockState getUnaffectedOxidationState(BlockState state) {
        return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
    }

    default Optional<BlockState> getDegradationResult(BlockState state) {
        return getIncreasedOxidationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
    }

    default float getDegradationChanceMultiplier() {
        return this.getDegradationLevel() == Oxidizable.OxidationLevel.UNAFFECTED ? 0.75F : 1.0F;
    }
}
