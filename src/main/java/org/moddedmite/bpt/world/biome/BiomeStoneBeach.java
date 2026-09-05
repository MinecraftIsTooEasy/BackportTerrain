package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;

public class BiomeStoneBeach extends BBiomes.BBiome {
    public BiomeStoneBeach(int id) {
        super(id);
        this.spawnableCreatureList.clear();
        this.surface(Block.stone, Block.stone);
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
}
