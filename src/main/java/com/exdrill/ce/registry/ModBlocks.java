package com.exdrill.ce.registry;

import com.exdrill.ce.CaveEnhancements;
import com.exdrill.ce.block.*;
import com.exdrill.ce.block.entity.LightningAnchorBlockEntity;
import com.exdrill.ce.block.entity.GoopTrapBlockEntity;
import com.exdrill.ce.block.entity.RoseQuartzChimesBlockEntity;
import com.exdrill.ce.block.entity.SpectacleCandleBlockEntity;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    
    // Block Components
    public static final Block GOOP_BLOCK = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).velocityMultiplier(0.3F).strength(0.5F, 1.0F).sounds(BlockSoundGroup.SLIME).jumpVelocityMultiplier(0.9F));
    public static final GoopTrapBlock GOOP_TRAP = new GoopTrapBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(1.0F, 5.0F).sounds(BlockSoundGroup.SLIME).velocityMultiplier( 0.01F).jumpVelocityMultiplier(0.3F).slipperiness(0.8F).mapColor(MapColor.PALE_YELLOW));
    public static final MultifaceBlock GOOP_SPLAT = new GoopSplatBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakInstantly().sounds(BlockSoundGroup.CORAL).noCollision().nonOpaque().mapColor(MapColor.PALE_YELLOW));
    public static final DrippingGoopBlock DRIPPING_GOOP = new DrippingGoopBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.CORAL).nonOpaque().noCollision().luminance(2).mapColor(MapColor.PALE_YELLOW));
    public static final GlowSplotchBlock GLOW_SPLOTCH = new GlowSplotchBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.HONEY).nonOpaque().noCollision().luminance(8).mapColor(MapColor.PALE_YELLOW));
    public static final SpectacleCandleBlock SPECTACLE_CANDLE = new SpectacleCandleBlock(FabricBlockSettings.of(Material.DECORATION).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).strength(0.1F, 0F));
    public static final Block LIGHTNING_ANCHOR = new LightningAnchorBlock(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER));
    public static final Block CHARGED_LIGHTNING_ANCHOR = new ChargedLightningAnchorBlock(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER).luminance((state) -> 15));
    public static final Block ROSE_QUARTZ_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));
    public static final Block ROSE_QUARTZ_CHIMES = new RoseQuartzChimesBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));
    public static final JaggedRoseQuartzBlock JAGGED_ROSE_QUARTZ = new JaggedRoseQuartzBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).noCollision().sounds(BlockSoundGroup.CALCITE));
    public static final Block POLISHED_ROSE_QUARTZ = new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));
    public static final Block ROSE_QUARTZ_TILES = new Block(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));
    public static final CustomSlabBlock POLISHED_ROSE_QUARTZ_SLAB = new CustomSlabBlock(FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));
    public static final CustomStairsBlock POLISHED_ROSE_QUARTZ_STAIRS = new CustomStairsBlock(POLISHED_ROSE_QUARTZ.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(0.8F, 10).requiresTool().mapColor(MapColor.PINK).sounds(BlockSoundGroup.CALCITE));



    // Block Registry
    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "goop_splat"), GOOP_SPLAT);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "dripping_goop"), DRIPPING_GOOP);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "glow_splotch"), GLOW_SPLOTCH);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "charged_lightning_anchor"), CHARGED_LIGHTNING_ANCHOR);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_block"), ROSE_QUARTZ_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_chimes"), ROSE_QUARTZ_CHIMES);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "jagged_rose_quartz"), JAGGED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "polished_rose_quartz"), POLISHED_ROSE_QUARTZ);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_tiles"), ROSE_QUARTZ_TILES);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "polished_rose_quartz_slab"), POLISHED_ROSE_QUARTZ_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(CaveEnhancements.NAMESPACE, "polished_rose_quartz_stairs"), POLISHED_ROSE_QUARTZ_STAIRS);
    }

    // Block Render Type
    public static void TransparentBlocks() {
        BlockRenderLayerMap.INSTANCE.putBlock(GOOP_SPLAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DRIPPING_GOOP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLOW_SPLOTCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SPECTACLE_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JAGGED_ROSE_QUARTZ, RenderLayer.getCutout());
    }

    // Block Entity
    public static BlockEntityType<SpectacleCandleBlockEntity> SPECTACLE_CANDLE_BLOCK_ENTITY;
    public static BlockEntityType<GoopTrapBlockEntity> GOOP_TRAP_BLOCK_ENTITY;
    public static BlockEntityType<LightningAnchorBlockEntity> LIGHTNING_ANCHOR_BLOCK_ENTITY;
    public static BlockEntityType<RoseQuartzChimesBlockEntity> ROSE_QUARTZ_CHIMES_BLOCK_ENTITY;

    // Block Entity Registry
    public static void registerBlockEntities() {
        GOOP_TRAP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "goop_trap"), FabricBlockEntityTypeBuilder.create(GoopTrapBlockEntity::new, GOOP_TRAP).build(null));
        SPECTACLE_CANDLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "spectacle_candle"), FabricBlockEntityTypeBuilder.create(SpectacleCandleBlockEntity::new, SPECTACLE_CANDLE).build(null));
        LIGHTNING_ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "lightning_anchor"), FabricBlockEntityTypeBuilder.create(LightningAnchorBlockEntity::new, LIGHTNING_ANCHOR).build(null));
        ROSE_QUARTZ_CHIMES_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CaveEnhancements.NAMESPACE, "rose_quartz_chimes"), FabricBlockEntityTypeBuilder.create(RoseQuartzChimesBlockEntity::new, ROSE_QUARTZ_CHIMES).build(null));
    }
}
