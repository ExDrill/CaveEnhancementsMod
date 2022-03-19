package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.item.AmethystFluteItem;
import com.exdrill.ce.item.GlowPasteItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
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
    public static final AmethystFluteItem AMETHYST_FLUTE = new AmethystFluteItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
    public static final SpawnEggItem GOOP_SPAWN_EGG = new SpawnEggItem(ModEntities.GOOP, 13946012, 11637089, new Item.Settings().group(ItemGroup.MISC));

    //Registry
    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "goop"), GOOP);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "dripping_goop"), DRIPPING_GOOP);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "glow_paste"), GLOW_PASTE);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "amethyst_flute"), AMETHYST_FLUTE);
        Registry.register(Registry.ITEM, new Identifier(Main.NAMESPACE, "goop_spawn_egg"), GOOP_SPAWN_EGG);
    }

}
