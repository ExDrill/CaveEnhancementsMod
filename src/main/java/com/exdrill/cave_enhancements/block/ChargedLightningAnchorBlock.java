package com.exdrill.cave_enhancements.block;

import com.exdrill.cave_enhancements.registry.ModBlocks;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class ChargedLightningAnchorBlock extends Block {
    public ChargedLightningAnchorBlock(FabricBlockSettings settings){
        super(settings);
    }

    private void knockBack(LivingEntity entity, BlockPos pos, double power, double verticlePower) {
        double d = entity.getX() - pos.getX();
        double e = entity.getZ() - pos.getZ();
        double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
        entity.addVelocity((d / f) * power, verticlePower, (e / f) * power);

        entity.velocityModified = true;
    }

    private boolean isPowered(World world, BlockPos pos){
        if(world.getEmittedRedstonePower(pos.down(), Direction.DOWN) > 0 && !world.getBlockState(pos.down()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }

        if(world.getEmittedRedstonePower(pos.up(), Direction.UP) > 0 && !world.getBlockState(pos.up()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }

        if(world.getEmittedRedstonePower(pos.east(), Direction.EAST) > 0 && !world.getBlockState(pos.east()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }

        if(world.getEmittedRedstonePower(pos.west(), Direction.WEST) > 0 && !world.getBlockState(pos.west()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }

        if(world.getEmittedRedstonePower(pos.north(), Direction.NORTH) > 0 && !world.getBlockState(pos.north()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }

        if (world.getEmittedRedstonePower(pos.south(), Direction.SOUTH) > 0 && !world.getBlockState(pos.south()).isOf(Blocks.LIGHTNING_ROD)){
            return true;
        }
        return false;
    }


    private void activate(World world, BlockPos pos, boolean interact, BlockPos fromPos){

        boolean powered = isPowered(world, pos);

        if((powered || interact)) {

            List<? extends LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(4.0D), (e) -> true);



            world.addSyncedBlockEvent(pos, this, 1, 0);

            double power = 0.9D;
            double verticalPower = 0.5D;

            LivingEntity livingEntity;
            for (Iterator<? extends LivingEntity> var2 = list.iterator(); var2.hasNext(); knockBack(livingEntity, pos, power, verticalPower)) {
                livingEntity = (LivingEntity) var2.next();
                livingEntity.damage(DamageSource.LIGHTNING_BOLT, 20.0F);
            }

            if (world.isClient) {
                world.addParticle(ModParticles.SHOCKWAVE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            }

            world.setBlockState(pos, ModBlocks.LIGHTNING_ANCHOR.getDefaultState());

            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        activate(world, pos, true, pos);

        return ActionResult.SUCCESS;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        activate(world, pos, false, fromPos);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        activate(world, pos, false, pos);
    }



    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        activate(world, pos, false, pos);
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        activate(world, pos, false, pos);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
}
