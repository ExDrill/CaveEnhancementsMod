package com.exdrill.ce.world.structures;

import com.exdrill.ce.Main;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;

public class MountainShrine extends StructureFeature<StructurePoolFeatureConfig> {

    public MountainShrine(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, MountainShrine::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of();

    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = Pool.of();

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);

        int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());

        VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(), spawnXZPosition.getZ(), context.world());

        BlockState topBlock = columnOfBlocks.getState(landHeight);

        return topBlock.getFluidState().isEmpty();
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!MountainShrine.isFeatureChunk(context)) {
            return Optional.empty();
        }

        StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(
                () -> context.registryManager().get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(Main.NAMESPACE, "mountain_shrine/start_pool")),
                10
        );

        StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.world(),
                context.validBiome(),
                context.structureManager(),
                context.registryManager()
        );

        BlockPos blockpos = context.chunkPos().getCenterAtY(0).down(14);

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator.generate(newContext, PoolStructurePiece::new, blockpos, false, true);

        if(structurePiecesGenerator.isPresent()) {
            System.out.println("Rundown House at " + blockpos);
        }

        return structurePiecesGenerator;
    }
}
