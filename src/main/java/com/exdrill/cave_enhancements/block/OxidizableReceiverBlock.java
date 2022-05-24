package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class OxidizableReceiverBlock extends ReceiverBlock implements OxidizableReceiver {
    public OxidizableReceiverBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(oxidationLevel, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(CAN_PASS, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CAN_PASS, POWERED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return OxidizableReceiver.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ConventionalItemTags.AXES) && !state.isOf(ModBlocks.REDSTONE_RECEIVER)) {
            if (state.isOf(ModBlocks.EXPOSED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }
            if (state.isOf(ModBlocks.WEATHERED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.EXPOSED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }
            if (state.isOf(ModBlocks.OXIDIZED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WEATHERED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }

            world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(player, 3005, pos, 0);
            itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
            }
            return ActionResult.SUCCESS;

        }
        if (itemStack.isOf(Items.HONEYCOMB)) {
            if (state.isOf(ModBlocks.REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WAXED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 4);
            }
            if (state.isOf(ModBlocks.EXPOSED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 5);
            }
            if (state.isOf(ModBlocks.WEATHERED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 6);
            }
            if (state.isOf(ModBlocks.OXIDIZED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 7);
            }
            world.syncWorldEvent(player, 3003, pos, 0);
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
            }

            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return ActionResult.SUCCESS;

        }
        return ActionResult.FAIL;
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
