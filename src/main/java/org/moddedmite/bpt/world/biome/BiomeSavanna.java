package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityHorse;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenSavannaTree;
import org.moddedmite.bpt.world.gen.feature.BWorldGenTrees;

import java.util.Random;

public class BiomeSavanna extends BBiomes.BBiome {
    public BiomeSavanna(int id) {
        super(id);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return rand.nextInt(5) > 0 ? new BWorldGenSavannaTree(false) : new BWorldGenTrees(false);
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        // TODO: tall-grass placement (WorldGenDoublePlant) unavailable in MITE
        super.decorate(world, rand, chunkX, chunkZ);
    }

    public BBiomes.BBiome createMutation() {
        Mutated mutated = new Mutated(this.biomeID + 128, this);
        mutated.temperature = (this.temperature + 1.0F) * 0.5F;
        mutated.minHeight = this.minHeight * 0.5F + 0.3F;
        mutated.maxHeight = this.maxHeight * 0.5F + 1.2F;
        return mutated;
    }

    public static class Mutated extends BiomeGenMutated {
        public Mutated(int id, BBiomes.BBiome base) {
            super(id, base);
            this.theBiomeDecorator.treesPerChunk = 2;
            this.theBiomeDecorator.flowersPerChunk = 2;
            this.theBiomeDecorator.grassPerChunk = 5;
        }

        public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metadata, int x, int z, double depth) {
            this.topBlock = Block.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Block.dirt;

            if (depth > 1.75D) {
                this.topBlock = Block.stone;
                this.fillerBlock = Block.stone;
            } else if (depth > -0.5D) {
                this.topBlock = Block.dirt;
                // TODO: 1.7.10 uses coarse dirt (dirt meta 1) here; unavailable in MITE
            }

            this.genBiomeTerrain(world, rand, blocks, metadata, x, z, depth);
        }
    }
}
