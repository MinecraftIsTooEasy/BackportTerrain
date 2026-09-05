package org.moddedmite.bpt.world.biome;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenMinable;
import net.minecraft.WorldGenTaiga2;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.api.IBiome;

import java.util.Random;

public class BiomeHills extends BBiomes.BBiome implements IBiome {
    private final WorldGenMinable silverfishGenerator;
    private final WorldGenTaiga2 spruceGenerator;
    private int field_150635_aE;
    private int field_150636_aF;
    private int field_150637_aG;
    private int field_150638_aH;

    public BiomeHills(int id, boolean extraTrees) {
        super(id);
        this.silverfishGenerator = new WorldGenMinable(Block.silverfish.blockID, 8);
        this.spruceGenerator = new WorldGenTaiga2(false);
        this.field_150635_aE = 0;
        this.field_150636_aF = 1;
        this.field_150637_aG = 2;
        this.field_150638_aH = this.field_150635_aE;
        if (extraTrees) {
            this.theBiomeDecorator.treesPerChunk = 3;
            this.field_150638_aH = this.field_150636_aF;
        }
    }
    
    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return rand.nextInt(3) > 0 ? this.spruceGenerator : super.getRandomWorldGenForTrees(rand);
    }
    
    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        super.decorate(world, rand, chunkX, chunkZ);
        int k = 3 + rand.nextInt(6);
        for (int l = 0; l < k; ++l) {
            int x = chunkX + rand.nextInt(16);
            int y = rand.nextInt(28) + 4;
            int z = chunkZ + rand.nextInt(16);
            if (world.getBlockId(x, y, z) == Block.stone.blockID) {
                world.setBlock(x, y, z, Block.oreEmerald.blockID, 0, 2);
            }
        }
        for (int i = 0; i < 7; ++i) {
            int x = chunkX + rand.nextInt(16);
            int y = rand.nextInt(64);
            int z = chunkZ + rand.nextInt(16);
            this.silverfishGenerator.generate(world, rand, x, y, z);
        }
    }
    
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        this.topBlock = Block.grass;
        this.field_150604_aj = 0;
        this.fillerBlock = Block.dirt;
        
        if ((p_150573_7_ < -1.0D || p_150573_7_ > 2.0D) && this.field_150638_aH == this.field_150637_aG) {
            this.topBlock = Block.gravel;
            this.fillerBlock = Block.gravel;
        } else if (p_150573_7_ > 1.0D && this.field_150638_aH != this.field_150636_aF) {
            this.topBlock = Block.stone;
            this.fillerBlock = Block.stone;
        }
        
        this.genBiomeTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }

//    public BiomeHills mutateHills(BBiomes.BBiome p_150633_1_) {
//        this.field_150638_aH = this.field_150637_aG;
//        this.func_150557_a(p_150633_1_.color, true);
//        this.setBiomeName(p_150633_1_.biomeName + " M");
//        this.setHeight(new BBiomes.Height(p_150633_1_.rootHeight, p_150633_1_.heightVariation));
//        this.setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
//        return this;
//    }
//
//    public BiomeGenBase createMutation() {
//        return (new BiomeHills(this.biomeID + 128, false)).mutateHills(this);
//    }
}
