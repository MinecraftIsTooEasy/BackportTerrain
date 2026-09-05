package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenDesertWells;

import java.util.Random;

public class BiomeDesert extends BBiomes.BBiome {
    public BiomeDesert(int id) {
        super(id);
        this.spawnableCreatureList.clear();
        this.surface(Block.sand, Block.sand);
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        super.decorate(world, rand, chunkX, chunkZ);

        if (rand.nextInt(1000) == 0) {
            int x = chunkX + rand.nextInt(16) + 8;
            int z = chunkZ + rand.nextInt(16) + 8;
            WorldGenDesertWells gen = new WorldGenDesertWells();
            gen.generate(world, rand, x, world.getHeightValue(x, z) + 1, z);
        }
    }
}
