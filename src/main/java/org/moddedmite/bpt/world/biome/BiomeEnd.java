package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityEnderman;
import net.minecraft.SpawnListEntry;

public class BiomeEnd extends BBiomes.BBiome {
    public BiomeEnd(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.surface(Block.dirt, Block.dirt);
    }

    public int getSkyColorByTemp(float currentTemperature) {
        return 0;
    }
}
