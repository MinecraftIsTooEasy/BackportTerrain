package org.moddedmite.bpt.world.biome;

import net.minecraft.ColorizerFoliage;
import net.minecraft.ColorizerGrass;
import net.minecraft.EntitySlime;
import net.minecraft.SpawnListEntry;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class BiomeSwamp extends BBiomes.BBiome {
    public BiomeSwamp(int id) {
        super(id);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 1;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.surface_mushrooms_per_chunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.theBiomeDecorator.sandPerChunk2 = 0;
        this.theBiomeDecorator.sandPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 5;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return this.worldGeneratorSwamp;
    }
    
    public int getBiomeGrassColor() {
        return ((ColorizerGrass.getGrassColor(this.getFloatTemperature(), this.getFloatRainfall()) & 16711422) + 5115470) / 2;
    }

    public int getBiomeFoliageColor() {
        return ((ColorizerFoliage.getFoliageColor(this.getFloatTemperature(), this.getFloatRainfall()) & 16711422) + 5115470) / 2;
    }
}
