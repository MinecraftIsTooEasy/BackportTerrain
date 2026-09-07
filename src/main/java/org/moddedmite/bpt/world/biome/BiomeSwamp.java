package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.ColorizerFoliage;
import net.minecraft.ColorizerGrass;
import net.minecraft.EntitySlime;
import net.minecraft.Material;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenSwamp;

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
        return new BWorldGenSwamp();
    }

    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metadata, int x, int z, double depth) {
        double d1 = BBiomes.plantNoise.func_151601_a((double) x * 0.25D, (double) z * 0.25D);

        if (d1 > 0.0D) {
            int k = x & 15;
            int l = z & 15;
            int i1 = blocks.length / 256;

            for (int j1 = 255; j1 >= 0; --j1) {
                int k1 = (l * 16 + k) * i1 + j1;

                if (blocks[k1] == null || blocks[k1].blockMaterial != Material.air) {
                    if (j1 == 62 && blocks[k1] != Block.waterStill) {
                        blocks[k1] = Block.waterStill;

                        if (d1 < 0.12D) {
                            blocks[k1 + 1] = Block.waterlily;
                        }
                    }

                    break;
                }
            }
        }

        this.genBiomeTerrain(world, rand, blocks, metadata, x, z, depth);
    }

    public int getBiomeGrassColor() {
        return ((ColorizerGrass.getGrassColor(this.getFloatTemperature(), this.getFloatRainfall()) & 16711422) + 5115470) / 2;
    }

    public int getBiomeFoliageColor() {
        return ((ColorizerFoliage.getFoliageColor(this.getFloatTemperature(), this.getFloatRainfall()) & 16711422) + 5115470) / 2;
    }
}
