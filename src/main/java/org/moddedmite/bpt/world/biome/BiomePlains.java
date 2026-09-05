package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityHorse;
import net.minecraft.SpawnListEntry;

public class BiomePlains extends BBiomes.BBiome {
    public BiomePlains(int id) {
        super(id);
        this.tempRain(0.8F, 0.4F);
        this.height(BBiomes.height_LowPlains);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
    }
}
