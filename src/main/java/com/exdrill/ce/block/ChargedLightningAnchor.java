package com.exdrill.ce.block;

import com.exdrill.ce.block.entity.ChargedLightningAnchorBlockEntity;
import com.exdrill.ce.registry.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ChargedLightningAnchor extends BlockWithEntity {
    public ChargedLightningAnchor(FabricBlockSettings settings){
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChargedLightningAnchorBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    private void knockBack(LivingEntity entity, BlockPos pos) {
        double d = entity.getX() - pos.getX();
        double e = entity.getZ() - pos.getZ();
        double f = Math.max(d * d + e * e, 0.001D);
        entity.addVelocity(d / f * 0.8D, 0.1D, e / f * 0.8D);

        entity.velocityModified = true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        List<? extends LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(8.0D), (e) -> {return true;});

        LivingEntity livingEntity;
        for(Iterator var2 = list.iterator(); var2.hasNext(); knockBack(livingEntity, pos)) {
            livingEntity = (LivingEntity)var2.next();
            livingEntity.damage(DamageSource.LIGHTNING_BOLT, 20.0F);
        }

        player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1, 1);
        world.setBlockState(pos, ModBlocks.LIGHTNING_ANCHOR.getDefaultState());

        return ActionResult.SUCCESS;
    }
}
