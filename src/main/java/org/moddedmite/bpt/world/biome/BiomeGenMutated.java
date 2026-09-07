package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.World;
import org.moddedmite.bpt.api.IBiomeDecorator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenAbstractTree;

import java.util.ArrayList;
import java.util.Random;

public class BiomeGenMutated extends BBiomes.BBiome {
    protected BBiomes.BBiome baseBiome;
    
    public BiomeGenMutated(int p_i45381_1_, BBiomes.BBiome p_i45381_2_) {
        super(p_i45381_1_);
        this.baseBiome = p_i45381_2_;
        this.setColor(p_i45381_2_.color, true);
        this.biomeName = p_i45381_2_.biomeName + " M";
        this.topBlock = p_i45381_2_.topBlock;
        this.fillerBlock = p_i45381_2_.fillerBlock;
        this.field_76754_C = p_i45381_2_.field_76754_C;
        this.rootHeight = p_i45381_2_.rootHeight;
        this.heightVariation = p_i45381_2_.heightVariation;
        this.temperature = p_i45381_2_.temperature;
        this.rainfall = p_i45381_2_.rainfall;
        this.waterColorMultiplier = p_i45381_2_.waterColorMultiplier;
        this.enableSnow = p_i45381_2_.enableSnow;
        this.enableRain = p_i45381_2_.enableRain;
        this.spawnableCreatureList = new ArrayList(p_i45381_2_.spawnableCreatureList);
        this.spawnableMonsterList = new ArrayList(p_i45381_2_.spawnableMonsterList);
        this.spawnableCaveCreatureList = new ArrayList(p_i45381_2_.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = new ArrayList(p_i45381_2_.spawnableWaterCreatureList);
        this.temperature = p_i45381_2_.temperature;
        this.rainfall = p_i45381_2_.rainfall;
        this.rootHeight = p_i45381_2_.rootHeight + 0.1F;
        this.heightVariation = p_i45381_2_.heightVariation + 0.2F;
        this.minHeight = p_i45381_2_.minHeight + 0.1F;
        this.maxHeight = p_i45381_2_.maxHeight + 0.2F;
    }
    
    public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_) {
        ((IBiomeDecorator) this.baseBiome.theBiomeDecorator).decorateChunk(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
    }
    
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        this.baseBiome.genTerrainBlocks(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance() {
        return this.baseBiome.getSpawningChance();
    }
    
    public BWorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return this.baseBiome.getRandomTreeFeature(rand);
    }
    
    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        return this.baseBiome.getBiomeFoliageColor();
    }
    
    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        return this.baseBiome.getBiomeGrassColor();
    }
    
    public Class<? extends BBiomes.BBiome> getBiomeClass() {
        return this.baseBiome.getBiomeClass();
    }
    
    /**
     * returns true if the biome specified is equal to this biome
     */
    public boolean isEqualTo(BBiomes.BBiome p_150569_1_) {
        return this.baseBiome.isEqualTo(p_150569_1_);
    }
    
    public BBiomes.TempCategory getTempCategory() {
        return this.baseBiome.getTempCategory();
    }
}