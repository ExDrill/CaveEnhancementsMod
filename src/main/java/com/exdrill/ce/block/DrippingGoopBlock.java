package com.exdrill.ce.block;

import com.exdrill.ce.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class DrippingGoopBlock extends Block implements Waterloggable {

    private static final BooleanProperty WATERLOGGED;

    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public static final VoxelShape SHAPE;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HANGING).add(new Property[]{WATERLOGGED});
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public DrippingGoopBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HANGING, true));
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean waterCheck = fluidState.getFluid() == Fluids.WATER;
        BlockState blockState = super.getDefaultState();
        return blockState.with(WATERLOGGED, waterCheck);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return direction == Direction.DOWN ? state.with(HANGING, this.HangingState(neighborState)) : direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext shapeContext) {
        return SHAPE;
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
