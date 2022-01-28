package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.entity.GoopEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<GoopEntity> GOOP = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Main.NAMESPACE, "goop"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GoopEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );
}
