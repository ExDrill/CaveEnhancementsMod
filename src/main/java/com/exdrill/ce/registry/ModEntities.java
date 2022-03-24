package com.exdrill.ce.registry;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.entity.BigGoopDripProjectile;
import com.exdrill.ce.entity.CruncherEntity;
import com.exdrill.ce.entity.DripstoneTortoiseEntity;
import com.exdrill.ce.entity.GoopEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<GoopEntity> GOOP = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CaveEnhancements.NAMESPACE, "goop"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GoopEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.9f))
                    .build()
    );

    public static final EntityType<CruncherEntity> CRUNCHER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CaveEnhancements.NAMESPACE, "cruncher"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CruncherEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8f, 0.8f))
                    .build()
    );

    public static final EntityType<DripstoneTortoiseEntity> DRIPSTONE_TORTOISE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CaveEnhancements.NAMESPACE, "dripstone_tortoise"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DripstoneTortoiseEntity::new)
                    .dimensions(EntityDimensions.fixed(1.3F, 0.8F))
                    .build()
    );

    public static final EntityType<BigGoopDripProjectile> BIG_GOOP_DRIP_PROJECTILE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CaveEnhancements.NAMESPACE, "big_goop_drip"),
            FabricEntityTypeBuilder.<BigGoopDripProjectile>create(SpawnGroup.MISC, BigGoopDripProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );

    public static void registerEntities() {
        FabricDefaultAttributeRegistry.register(GOOP, GoopEntity.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(CRUNCHER, CruncherEntity.createCruncherAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_TORTOISE, DripstoneTortoiseEntity.createDripstoneTortoiseAttributes());
    }
}
