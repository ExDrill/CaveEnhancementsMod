package com.exdrill.cave_enhancements.item;

import com.exdrill.cave_enhancements.entity.JingleArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class JingleArrowItem extends ArrowItem {

    public JingleArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new JingleArrowEntity(world, shooter);
    }
}
