package org.moddedmite.bpt.world.biome;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.Material;
import net.minecraft.World;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.NoiseGeneratorPerlin;

import java.util.Arrays;
import java.util.Random;

public class BiomeMesa extends BBiomes.BBiome {
    private byte[] clayBands;
    private long seed;
    private NoiseGeneratorPerlin bryceNoise;
    private NoiseGeneratorPerlin bryceDetailNoise;
    private NoiseGeneratorPerlin clayBandNoise;
    private final boolean bryce;
    private final boolean hasForest;

    public BiomeMesa(int id, boolean bryce, boolean hasForest) {
        super(id);
        this.bryce = bryce;
        this.hasForest = hasForest;
        this.noRain();
        this.tempRain(2.0F, 0.0F);
        this.spawnableCreatureList.clear();
        this.surface(Block.sand, Block.stainedClay);
        // TODO: FutureMITE redsand
        this.field_150604_aj = 0;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();

        if (hasForest) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return this.worldGeneratorTrees;
    }

    public int getBiomeGrassColor() {
        return 9470285;
    }

    public int getBiomeFoliageColor() {
        return 10387789;
    }

    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metadata, int x, int z, double depth) {
        if (this.clayBands == null || this.seed != world.getSeed()) {
            this.initClayBands(world.getSeed());
        }

        if (this.bryceNoise == null || this.bryceDetailNoise == null || this.seed != world.getSeed()) {
            Random random = new Random(this.seed);
            this.bryceNoise = new NoiseGeneratorPerlin(random, 4);
            this.bryceDetailNoise = new NoiseGeneratorPerlin(random, 1);
        }

        this.seed = world.getSeed();
        double d5 = 0.0D;
        int k;
        int l;

        if (this.bryce) {
            k = (x & -16) + (z & 15);
            l = (z & -16) + (x & 15);
            double d1 = Math.min(Math.abs(depth), this.bryceNoise.func_151601_a((double) k * 0.25D, (double) l * 0.25D));

            if (d1 > 0.0D) {
                double d2 = 0.001953125D;
                double d3 = Math.abs(this.bryceDetailNoise.func_151601_a((double) k * d2, (double) l * d2));
                d5 = d1 * d1 * 2.5D;
                double d4 = Math.ceil(d3 * 50.0D) + 14.0D;

                if (d5 > d4) {
                    d5 = d4;
                }

                d5 += 64.0D;
            }
        }

        k = x & 15;
        l = z & 15;
        Block block = Block.stainedClay;
        Block block2 = this.fillerBlock;
        int i1 = (int) (depth / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        boolean flag1 = Math.cos(depth / 3.0D * Math.PI) > 0.0D;
        int j1 = -1;
        boolean flag2 = false;
        int k1 = blocks.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (l * 16 + k) * k1 + l1;

            if ((blocks[i2] == null || blocks[i2].blockMaterial == Material.air) && l1 < (int) d5) {
                blocks[i2] = Block.stone;
            }

            if (l1 <= 0 + rand.nextInt(5)) {
                blocks[i2] = Block.bedrock;
            } else {
                Block block1 = blocks[i2];

                if (block1 != null && block1.blockMaterial != Material.air) {
                    if (block1 == Block.stone) {
                        byte b0;

                        if (j1 == -1) {
                            flag2 = false;

                            if (i1 <= 0) {
                                block = null;
                                block2 = Block.stone;
                            } else if (l1 >= 59 && l1 <= 64) {
                                block = Block.stainedClay;
                                block2 = this.fillerBlock;
                            }

                            if (l1 < 63 && (block == null || block.blockMaterial == Material.air)) {
                                block = Block.waterStill;
                            }

                            j1 = i1 + Math.max(0, l1 - 63);

                            if (l1 >= 62) {
                                if (this.hasForest && l1 > 86 + i1 * 2) {
                                    if (flag1) {
                                        blocks[i2] = Block.dirt;
                                        metadata[i2] = 1;
                                    } else {
                                        blocks[i2] = Block.grass;
                                    }
                                } else if (l1 > 66 + i1) {
                                    b0 = 16;

                                    if (l1 >= 64 && l1 <= 127) {
                                        if (!flag1) {
                                            b0 = this.getClayColor(x, l1, z);
                                        }
                                    } else {
                                        b0 = 1;
                                    }

                                    if (b0 < 16) {
                                        blocks[i2] = Block.stainedClay;
                                        metadata[i2] = b0;
                                    } else {
                                        blocks[i2] = Block.hardenedClay;
                                    }
                                } else {
                                    blocks[i2] = this.topBlock;
                                    metadata[i2] = (byte) this.field_150604_aj;
                                    flag2 = true;
                                }
                            } else {
                                blocks[i2] = block2;

                                if (block2 == Block.stainedClay) {
                                    metadata[i2] = 1;
                                }
                            }
                        } else if (j1 > 0) {
                            --j1;

                            if (flag2) {
                                blocks[i2] = Block.stainedClay;
                                metadata[i2] = 1;
                            } else {
                                b0 = this.getClayColor(x, l1, z);

                                if (b0 < 16) {
                                    blocks[i2] = Block.stainedClay;
                                    metadata[i2] = b0;
                                } else {
                                    blocks[i2] = Block.hardenedClay;
                                }
                            }
                        }
                    }
                } else {
                    j1 = -1;
                }
            }
        }
    }

    public void initClayBands(long seed) {
        this.clayBands = new byte[64];
        Arrays.fill(this.clayBands, (byte) 16);
        Random random = new Random(seed);
        this.clayBandNoise = new NoiseGeneratorPerlin(random, 1);
        int j;

        for (j = 0; j < 64; ++j) {
            j += random.nextInt(5) + 1;

            if (j < 64) {
                this.clayBands[j] = 1;
            }
        }

        j = random.nextInt(4) + 2;
        int k;
        int l;
        int i1;
        int j1;

        for (k = 0; k < j; ++k) {
            l = random.nextInt(3) + 1;
            i1 = random.nextInt(64);

            for (j1 = 0; i1 + j1 < 64 && j1 < l; ++j1) {
                this.clayBands[i1 + j1] = 4;
            }
        }

        k = random.nextInt(4) + 2;
        int k1;

        for (l = 0; l < k; ++l) {
            i1 = random.nextInt(3) + 2;
            j1 = random.nextInt(64);

            for (k1 = 0; j1 + k1 < 64 && k1 < i1; ++k1) {
                this.clayBands[j1 + k1] = 12;
            }
        }

        l = random.nextInt(4) + 2;

        for (i1 = 0; i1 < l; ++i1) {
            j1 = random.nextInt(3) + 1;
            k1 = random.nextInt(64);

            for (int l1 = 0; k1 + l1 < 64 && l1 < j1; ++l1) {
                this.clayBands[k1 + l1] = 14;
            }
        }

        i1 = random.nextInt(3) + 3;
        j1 = 0;

        for (k1 = 0; k1 < i1; ++k1) {
            byte b0 = 1;
            j1 += random.nextInt(16) + 4;

            for (int i2 = 0; j1 + i2 < 64 && i2 < b0; ++i2) {
                this.clayBands[j1 + i2] = 0;

                if (j1 + i2 > 1 && random.nextBoolean()) {
                    this.clayBands[j1 + i2 - 1] = 8;
                }

                if (j1 + i2 < 63 && random.nextBoolean()) {
                    this.clayBands[j1 + i2 + 1] = 8;
                }
            }
        }
    }

    public byte getClayColor(int x, int y, int z) {
        int l = (int) Math.round(this.clayBandNoise.func_151601_a((double) x * 1.0D / 512.0D, (double) x * 1.0D / 512.0D) * 2.0D);
        return this.clayBands[(y + l + 64) % 64];
    }

    public BBiomes.BBiome createMutation() {
        boolean flag = this.biomeID == BBiomes.mesa.biomeID;
        BiomeMesa biomegenmesa = new BiomeMesa(this.biomeID + 128, flag, this.hasForest);
        
        if (!flag) {
            biomegenmesa.height(BBiomes.height_LowHills);
            biomegenmesa.setBiomeName(this.biomeName + " M");
        } else {
            biomegenmesa.setBiomeName(this.biomeName + " (Bryce)");
        }
        
        biomegenmesa.setColor(this.color, true);
        return biomegenmesa;
    }
}
