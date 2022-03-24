package com.exdrill.ce.registry;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.item.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Items
    public static final BlockItem GOOP = new BlockItem(ModBlocks.GOOP_SPLAT, new Item.Settings().group(ItemGroup.MATERIALS));
    public static final BlockItem GOOP_BLOCK = new BlockItem(ModBlocks.GOOP_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem GOOP_TRAP = new BlockItem(ModBlocks.GOOP_TRAP, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem DRIPPING_GOOP = new BlockItem(ModBlocks.DRIPPING_GOOP, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final GlowPasteItem GLOW_PASTE = new GlowPasteItem(new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(1).maxDamage(24));
    public static final BlockItem SPECTACLE_CANDLE = new BlockItem(ModBlocks.SPECTACLE_CANDLE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem LIGHTNING_ANCHOR = new BlockItem(ModBlocks.LIGHTNING_ANCHOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem CHARGED_LIGHTNING_ANCHOR = new BlockItem(ModBlocks.CHARGED_LIGHTNING_ANCHOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final AmethystFluteItem AMETHYST_FLUTE = new AmethystFluteItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(64));
    public static final SpawnEggItem GOOP_SPAWN_EGG = new SpawnEggItem(ModEntities.GOOP, 13946012, 11637089, new Item.Settings().group(ItemGroup.MISC));

    public static final GoopBucketItem GOOP_BUCKET = new GoopBucketItem(ModEntities.GOOP, Fluids.EMPTY, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(ItemGroup.MISC).maxCount(1));
    public static final Item BIG_GOOP_DRIP = new Item(new Item.Settings());

    // Unreleased Content
    public static final CustomSpawnEggItem CRUNCHER_SPAWN_EGG = new CustomSpawnEggItem(ModEntities.CRUNCHER, 11127234, 5757312, new Item.Settings().group(ItemGroup.MISC));
    public static final CustomSpawnEggItem DRIPSTONE_TORTOISE_SPAWN_EGG = new CustomSpawnEggItem(ModEntities.DRIPSTONE_TORTOISE, 8156236, 6967114, new Item.Settings().group(ItemGroup.MISC));
    public static final CustomBlockItem ROSE_QUARTZ_BLOCK = new CustomBlockItem(ModBlocks.ROSE_QUARTZ_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final CustomBlockItem JAGGED_ROSE_QUARTZ = new CustomBlockItem(ModBlocks.JAGGED_ROSE_QUARTZ, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final CustomItem ROSE_QUARTZ = new CustomItem(new Item.Settings().group(ItemGroup.MATERIALS));

    //Registry
    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "goop"), GOOP);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "dripping_goop"), DRIPPING_GOOP);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "glow_paste"), GLOW_PASTE);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "amethyst_flute"), AMETHYST_FLUTE);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "goop_spawn_egg"), GOOP_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "goop_bucket"), GOOP_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "big_goop_drip"), BIG_GOOP_DRIP);

        // Unreleased Content
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_block"), ROSE_QUARTZ_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "jagged_rose_quartz"), JAGGED_ROSE_QUARTZ);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz"), ROSE_QUARTZ);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "cruncher_spawn_egg"), CRUNCHER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(CaveEnhancements.NAMESPACE, "dripstone_tortoise_spawn_egg"), DRIPSTONE_TORTOISE_SPAWN_EGG);
    }

}
