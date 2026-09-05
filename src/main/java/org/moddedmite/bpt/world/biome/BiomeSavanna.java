package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityHorse;
import net.minecraft.SpawnListEntry;
import net.minecraft.WorldGenerator;

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
        // TODO: savanna tree (WorldGenSavannaTree) is missing in MITE, fall back to oak
        return this.worldGeneratorTrees;
    }
}
