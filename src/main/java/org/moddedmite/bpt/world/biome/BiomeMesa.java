package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.WorldGenerator;

import java.util.Random;

// TODO: mesa layered clay bands / red sand (genTerrainBlocks) are not portable to MITE's terrain generator
public class BiomeMesa extends BBiomes.BBiome {
    public BiomeMesa(int id, boolean bryce, boolean hasForest) {
        super(id);
        this.noRain();
        this.tempRain(2.0F, 0.0F);
        this.spawnableCreatureList.clear();
        this.surface(Block.sand, Block.stainedClay);
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();

        if (hasForest) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return this.worldGeneratorTrees;
    }

    public int getBiomeGrassColor() {
        return 9470285;
    }

    public int getBiomeFoliageColor() {
        return 10387789;
    }
}
