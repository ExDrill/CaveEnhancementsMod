package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import com.exdrill.cave_enhancements.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap.Type;

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

    public static final EntityType<DripstonePikeEntity> DRIPSTONE_PIKE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CaveEnhancements.NAMESPACE, "dripstone_pike"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, DripstonePikeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.3F, 0.3F))
                    .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(GOOP, GoopEntity.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(CRUNCHER, CruncherEntity.createCruncherAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_TORTOISE, DripstoneTortoiseEntity.createDripstoneTortoiseAttributes());
        FabricDefaultAttributeRegistry.register(DRIPSTONE_PIKE, DripstonePikeEntity.createDripstonePikeAttributes());
        SpawnRestrictionAccessor.callRegister(DRIPSTONE_TORTOISE, SpawnRestriction.Location.ON_GROUND, Type.MOTION_BLOCKING, DripstoneTortoiseEntity::canSpawnInDark);
        SpawnRestrictionAccessor.callRegister(CRUNCHER, SpawnRestriction.Location.ON_GROUND, Type.MOTION_BLOCKING, CruncherEntity::canMobSpawn);
    }
}
