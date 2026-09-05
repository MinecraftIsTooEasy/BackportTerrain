package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityMooshroom;
import net.minecraft.SpawnListEntry;

public class BiomeMushroomIsland extends BBiomes.BBiome {
    public BiomeMushroomIsland(int id) {
        super(id);
        this.theBiomeDecorator.treesPerChunk = -100;
        this.theBiomeDecorator.flowersPerChunk = -100;
        this.theBiomeDecorator.grassPerChunk = -100;
        this.theBiomeDecorator.surface_mushrooms_per_chunk = 1;
        this.theBiomeDecorator.bigMushroomsPerChunk = 1;
        this.surface(Block.mycelium, Block.dirt);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
    }
}
