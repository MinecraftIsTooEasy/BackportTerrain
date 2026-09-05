package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityGhast;
import net.minecraft.EntityMagmaCube;
import net.minecraft.EntityPigZombie;
import net.minecraft.SpawnListEntry;

public class BiomeHell extends BBiomes.BBiome {
    public BiomeHell(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
    }
}
