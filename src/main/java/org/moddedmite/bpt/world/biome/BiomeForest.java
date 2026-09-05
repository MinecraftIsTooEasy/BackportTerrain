package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityWolf;
import net.minecraft.SpawnListEntry;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class BiomeForest extends BBiomes.BBiome {
    private final int type;

    public BiomeForest(int id, int type) {
        super(id);
        this.type = type;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;

        if (this.type == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }

        this.grassColor(5159473);
        this.tempRain(0.7F, 0.8F);

        if (this.type == 2) {
            this.color = 3175492;
            this.tempRain(0.6F, 0.6F);
        }

        if (this.type == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }

        if (this.type == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        // TODO: roofed forest canopy tree (WorldGenCanopyTree) is missing in MITE, fall back to oak/birch
        if (this.type == 2) {
            return this.worldGeneratorForest;
        }
        return rand.nextInt(5) == 0 ? this.worldGeneratorForest : this.worldGeneratorTrees;
    }
}
