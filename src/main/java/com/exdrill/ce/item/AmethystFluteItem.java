package com.exdrill.ce.item;


import com.exdrill.ce.registry.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class AmethystFluteItem extends Item {
    private static final long fearDuration = 40L;
    public AmethystFluteItem(Settings settings) {
        super(settings);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        double x = user.getX();
        double y = user.getEyeY();
        double z = user.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        if (!world.isClient) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            ((ServerWorld)world).spawnParticles(ModParticles.SOOTHINGNOTE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 3, 0, 0, 0, 0);
            user.world.getOtherEntities(user, user.getBoundingBox().expand(10D), user::canSee).forEach(entity -> {
                if (entity instanceof RavagerEntity ravagerEntity) {
                    ravagerEntity.handleStatus((byte)39);
                    ravagerEntity.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0F, 1.0F);
                    world.sendEntityStatus(ravagerEntity, (byte)39);
                } else if (entity instanceof VexEntity vexEntity) {
                    vexEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0));
                }
            });

            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putLong("AmethystFluteScary", world.getTime());
            itemStack.setNbt(nbt);
            user.getItemCooldownManager().set(this, 400);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @ParametersAreNonnullByDefault
    public static boolean isScary(LivingEntity livingEntity) {
        long scaryTime = 0L;
        if (livingEntity.getMainHandStack().getItem() instanceof AmethystFluteItem) {
            scaryTime = livingEntity.getMainHandStack().getOrCreateNbt().getLong("AmethystFluteScary");
        }
        if (livingEntity.getOffHandStack().getItem() instanceof AmethystFluteItem) {
            scaryTime = Math.max(scaryTime, livingEntity.getOffHandStack().getOrCreateNbt().getLong("AmethystFluteScary"));
        }
        return scaryTime != 0L && livingEntity.world.getTime() - scaryTime <= fearDuration;
    }
}

