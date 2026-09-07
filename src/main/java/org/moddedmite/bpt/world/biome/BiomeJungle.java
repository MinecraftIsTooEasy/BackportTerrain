package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityChicken;
import net.minecraft.EntityOcelot;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;
import net.minecraft.WorldGenTallGrass;
import net.minecraft.WorldGenVines;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenBigTree;
import org.moddedmite.bpt.world.gen.feature.BWorldGenMegaJungle;
import org.moddedmite.bpt.world.gen.feature.BWorldGenShrub;
import org.moddedmite.bpt.world.gen.feature.BWorldGenTrees;

import java.util.Random;

public class BiomeJungle extends BBiomes.BBiome {
    private final boolean isEdge;

    public BiomeJungle(int id, boolean isEdge) {
        super(id);
        this.isEdge = isEdge;

        if (isEdge) {
            this.theBiomeDecorator.treesPerChunk = 2;
        } else {
            this.theBiomeDecorator.treesPerChunk = 50;
        }

        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;

        if (!isEdge) {
            this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }

        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        if (rand.nextInt(10) == 0) {
            return new BWorldGenBigTree(false);
        }
        if (rand.nextInt(2) == 0) {
            return new BWorldGenShrub(3, 0);
        }
        if (!this.isEdge && rand.nextInt(3) == 0) {
            return new BWorldGenMegaJungle(false, 10, 20, 3, 3);
        } else {
            return new BWorldGenTrees(false, 4 + rand.nextInt(7), 3, 3, true);
        }
    }

    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        super.decorate(world, rand, chunkX, chunkZ);
        WorldGenVines vines = new WorldGenVines();

        for (int i = 0; i < 50; ++i) {
            int x = chunkX + rand.nextInt(16) + 8;
            int z = chunkZ + rand.nextInt(16) + 8;
            vines.generate(world, rand, x, 128, z);
        }
    }
}
