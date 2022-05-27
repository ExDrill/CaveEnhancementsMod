package com.exdrill.cave_enhancements.entity;

import com.exdrill.cave_enhancements.registry.ModEntities;
import com.exdrill.cave_enhancements.registry.ModItems;
import com.exdrill.cave_enhancements.registry.ModParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class JingleArrowEntity extends PersistentProjectileEntity {
    private double damage;


    public JingleArrowEntity(EntityType<? extends JingleArrowEntity> entityType, World world) {
        super(entityType, world);
        this.damage = 1.0D;

    }

    public JingleArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.JINGLE_ARROW, owner, world);
    }

    public JingleArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.JINGLE_ARROW, x, y, z, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient && !this.inGround) {
            this.world.addParticle(ParticleTypes.WAX_OFF, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        knockbackEntitiesAround(0.35F);
        world.addParticle(ModParticles.AMETHYST_BLAST, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onHit(LivingEntity target) {
        knockbackEntitiesAround(0.1F);
    }

    public void knockbackEntitiesAround( float knockbackStrength ) {

        Box box = new Box(this.getBlockPos());
        List<? extends LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box.expand(3), (entity) -> true);

        for (LivingEntity entity : list) {
            if (entity != null) {
                entity.takeKnockback(knockbackStrength, this.getX() - entity.getX(), this.getZ() - entity.getZ());
            }
        }

        if (!world.isClient) {
            this.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.JINGLE_ARROW);
    }

}
