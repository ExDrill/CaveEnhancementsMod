package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import com.exdrill.ce.block.*;
import com.exdrill.ce.block.entity.ChargedLightningAnchorBlockEntity;
import com.exdrill.ce.block.entity.LightningAnchorBlockEntity;
import com.exdrill.ce.block.entity.GoopTrapBlockEntity;
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
    public static final Block GOOP_BLOCK = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakByHand(true).velocityMultiplier(0.3F).strength(0.5F, 1.0F).sounds(BlockSoundGroup.SLIME).jumpVelocityMultiplier(0.8F));
    public static final GoopTrapBlock GOOP_TRAP = new GoopTrapBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakByHand(true).strength(1.0F, 5.0F).sounds(BlockSoundGroup.SLIME).velocityMultiplier( 0.01F).jumpVelocityMultiplier(0.3F).slipperiness(0.8F).mapColor(MapColor.PALE_YELLOW));
    public static final MultifaceBlock GOOP_SPLAT = new GoopSplatBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakByHand(true).breakInstantly().sounds(BlockSoundGroup.CORAL).noCollision().nonOpaque().mapColor(MapColor.PALE_YELLOW));
    public static final DrippingGoopBlock DRIPPING_GOOP = new DrippingGoopBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakByHand(true).sounds(BlockSoundGroup.CORAL).nonOpaque().noCollision().luminance(2).mapColor(MapColor.PALE_YELLOW));
    public static final GlowSplotchBlock GLOW_SPLOTCH = new GlowSplotchBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).breakByHand(true).sounds(BlockSoundGroup.HONEY).nonOpaque().noCollision().luminance(8).mapColor(MapColor.PALE_YELLOW));
    public static final SpectacleCandleBlock SPECTACLE_CANDLE = new SpectacleCandleBlock(FabricBlockSettings.of(Material.DECORATION).breakByHand(true).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE));
    public static final Block LIGHTNING_ANCHOR = new LightningAnchor(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER));
    public static final Block CHARGED_LIGHTNING_ANCHOR = new Block(FabricBlockSettings.of(Material.METAL).strength(4, 100).requiresTool().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.COPPER));

    // Block Registry
    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "goop_block"), GOOP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "goop_splat"), GOOP_SPLAT);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "goop_trap"), GOOP_TRAP);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "dripping_goop"), DRIPPING_GOOP);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "glow_splotch"), GLOW_SPLOTCH);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "spectacle_candle"), SPECTACLE_CANDLE);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "lightning_anchor"), LIGHTNING_ANCHOR);
        Registry.register(Registry.BLOCK, new Identifier(Main.NAMESPACE, "lightning_anchor_charged"), CHARGED_LIGHTNING_ANCHOR);
    }

    // Block Render Type
    public static void TransparentBlocks() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOOP_SPLAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIPPING_GOOP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLOW_SPLOTCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPECTACLE_CANDLE, RenderLayer.getCutout());
    }

    public static BlockEntityType<SpectacleCandleBlockEntity> SPECTACLE_CANDLE_BLOCK_ENTITY;
    public static BlockEntityType<GoopTrapBlockEntity> GOOP_TRAP_BLOCK_ENTITY;
    public static BlockEntityType<LightningAnchorBlockEntity> LIGHTNING_ANCHOR_BLOCK_ENTITY;
    public static BlockEntityType<ChargedLightningAnchorBlockEntity> CHARGED_LIGHTNING_ANCHOR_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        GOOP_TRAP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ce:goop_trap", FabricBlockEntityTypeBuilder.create(GoopTrapBlockEntity::new, GOOP_TRAP).build(null));
        SPECTACLE_CANDLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ce:spectacle_candle", FabricBlockEntityTypeBuilder.create(SpectacleCandleBlockEntity::new, SPECTACLE_CANDLE).build(null));
        LIGHTNING_ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.NAMESPACE, "lightning_anchor_block_entity"), FabricBlockEntityTypeBuilder.create(LightningAnchorBlockEntity::new, LIGHTNING_ANCHOR).build(null));
        CHARGED_LIGHTNING_ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.NAMESPACE, "lightning_anchor_block_entity_charged"), FabricBlockEntityTypeBuilder.create(ChargedLightningAnchorBlockEntity::new, CHARGED_LIGHTNING_ANCHOR).build(null));
    }
}
