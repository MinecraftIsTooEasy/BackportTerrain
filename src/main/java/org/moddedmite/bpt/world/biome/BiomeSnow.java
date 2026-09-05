package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.WorldGenTaiga2;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class BiomeSnow extends BBiomes.BBiome {
    private final boolean superIcy;

    public BiomeSnow(int id, boolean superIcy) {
        super(id);
        this.superIcy = superIcy;

        if (superIcy) {
            this.surface(Block.snow, Block.snow);
        }

        this.spawnableCreatureList.clear();
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return new WorldGenTaiga2(false);
    }
}
