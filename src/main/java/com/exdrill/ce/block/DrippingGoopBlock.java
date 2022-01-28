package com.exdrill.ce.block;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DrippingGoopBlock extends Block {
    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public static final VoxelShape SHAPE;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HANGING);
    }

    public DrippingGoopBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HANGING, true));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN ? (BlockState)state.with(HANGING, this.HangingState(neighborState)) : direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean HangingState(BlockState state) {
        return !state.isOf(ModBlocks.DRIPPING_GOOP);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isOf(ModBlocks.DRIPPING_GOOP);
    }

    public static boolean canDrip(BlockState state) {
        return (boolean) state.get(HANGING);
    }

    static {
        SHAPE = Block.createCuboidShape(1,0,1,14,16,14);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext shapeContext) {
        return SHAPE;
    }
}
