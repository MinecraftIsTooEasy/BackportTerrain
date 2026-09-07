package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityWolf;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;
import net.minecraft.WorldGenBigMushroom;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenCanopyTree;
import org.moddedmite.bpt.world.gen.feature.BWorldGenForest;
import org.moddedmite.bpt.world.gen.feature.BWorldGenTrees;

import java.util.Random;

public class BiomeForest extends BBiomes.BBiome {
    private final int type;

    public BiomeForest(int id, int type) {
        super(id);
        this.type = type;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;

        if (this.type == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }

        this.grassColor(5159473);
        this.tempRain(0.7F, 0.8F);

        if (this.type == 2) {
            this.color = 3175492;
            this.tempRain(0.6F, 0.6F);
        }

        if (this.type == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }

        if (this.type == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        if (this.type == 3 && rand.nextInt(3) > 0) {
            return new BWorldGenCanopyTree(false);
        }
        if (this.type != 2 && rand.nextInt(5) != 0) {
            return new BWorldGenTrees(false);
        }
        return new BWorldGenForest(false, false);
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        if (this.type == 3) {
            for (int k = 0; k < 4; ++k) {
                for (int l = 0; l < 4; ++l) {
                    int x = chunkX + k * 4 + 1 + 8 + rand.nextInt(3);
                    int z = chunkZ + l * 4 + 1 + 8 + rand.nextInt(3);
                    int y = world.getHeightValue(x, z);

                    if (rand.nextInt(20) == 0) {
                        new WorldGenBigMushroom().generate(world, rand, x, y, z);
                    } else {
                        WorldGenerator tree = this.getRandomWorldGenForTrees(rand);
                        tree.setScale(1.0D, 1.0D, 1.0D);
                        tree.generate(world, rand, x, y, z);
                    }
                }
            }
        }

        // TODO: flower forest (type 1) tall-flower placement needs WorldGenDoublePlant, which is unavailable in MITE
        super.decorate(world, rand, chunkX, chunkZ);
    }

    public int getBiomeGrassColor() {
        int color = super.getBiomeGrassColor();
        return this.type == 3 ? (color & 16711422) + 2634762 >> 1 : color;
    }

    public BBiomes.BBiome createMutation() {
        if (this.biomeID == BBiomes.forest.biomeID) {
            BiomeForest flowerForest = new BiomeForest(this.biomeID + 128, 1);
            flowerForest.height(new BBiomes.Height(this.minHeight, this.maxHeight + 0.2F));
            flowerForest.name("Flower Forest");
            flowerForest.setColor(6976549, true);
            flowerForest.grassColor(8233509);
            return flowerForest;
        }

        if (this.biomeID != BBiomes.birchForest.biomeID && this.biomeID != BBiomes.birchForestHills.biomeID) {
            return new BiomeGenMutated(this.biomeID + 128, this) {
                @Override
                public void decorate(World world, Random rand, int chunkX, int chunkZ) {
                    this.baseBiome.decorate(world, rand, chunkX, chunkZ);
                }
            };
        }

        return new BiomeGenMutated(this.biomeID + 128, this) {
            @Override
            public WorldGenerator getRandomWorldGenForTrees(Random rand) {
                return rand.nextBoolean() ? new BWorldGenForest(false, true) : new BWorldGenForest(false, false);
            }
        };
    }
}
