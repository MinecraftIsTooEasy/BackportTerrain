package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;

public class BiomeBeach extends BBiomes.BBiome {
    public BiomeBeach(int id) {
        super(id);
        this.spawnableCreatureList.clear();
        this.surface(Block.sand, Block.sand);
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
}
