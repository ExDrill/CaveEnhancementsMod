package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.block.entity.ReceiverBlockEntity;
import com.exdrill.cave_enhancements.registry.ModBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ReceiverBlock extends AbstractRedstoneGateBlock implements BlockEntityProvider {
    public static final BooleanProperty CAN_PASS = BooleanProperty.of("can_pass");
    public Oxidizable.OxidationLevel oxidationLevel;

    public ReceiverBlock(Oxidizable.OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(CAN_PASS, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CAN_PASS, POWERED);
    }

    protected boolean isValidInput(BlockState state) {
        return isRedstoneGate(state);
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return this.getPower((World) world, pos, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            Direction direction = state.get(FACING);
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float g = -5.0F;
            if (random.nextBoolean()) {
                g = (float)(4 * 2 - 1);
            }

            g /= 16.0F;
            double h = g * (float)direction.getOffsetX();
            double i = g * (float)direction.getOffsetZ();
            world.addParticle(DustParticleEffect.DEFAULT, d + h, e, f + i, 0.0D, 0.0D, 0.0D);
        }
    }


    public int getPower(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction);
        int i = world.getEmittedRedstonePower(blockPos, direction);
        if (i >= 15) {
            return i;
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            return Math.max(i, blockState.isOf(Blocks.REDSTONE_WIRE) ? blockState.get(RedstoneWireBlock.POWER) : 0);
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!state.get(CAN_PASS)) {
            return 0;
        } else {
            return state.get(FACING) == direction ? this.getOutputLevel(world, pos, state) : 0;
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return state.get(CAN_PASS);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0;
    }



    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        assert blockState != null;
        return blockState.with(CAN_PASS, false).with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    public int getMaxPower() {
        return switch (this.oxidationLevel) {
            case UNAFFECTED -> 2;
            case EXPOSED -> 5;
            case WEATHERED -> 10;
            case OXIDIZED -> 20;
        };
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // Block Entity
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReceiverBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof ReceiverBlockEntity) {
                ((ReceiverBlockEntity) blockEntity).tick(world1, pos, state1, blockEntity);
            }
        };
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ConventionalItemTags.AXES)) {
            if (state.isOf(ModBlocks.WAXED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }
            if (state.isOf(ModBlocks.WAXED_EXPOSED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.EXPOSED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }
            if (state.isOf(ModBlocks.WAXED_WEATHERED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.WEATHERED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);
            }
            if (state.isOf(ModBlocks.WAXED_OXIDIZED_REDSTONE_RECEIVER)) {
                world.setBlockState(pos, ModBlocks.OXIDIZED_REDSTONE_RECEIVER.getDefaultState().with(FACING, state.get(FACING)), 3);

            }

            world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);


            itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            world.syncWorldEvent(player, 3004, pos, 0);

            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
            }
            return ActionResult.SUCCESS;

        }
        return ActionResult.FAIL;
    }
}
