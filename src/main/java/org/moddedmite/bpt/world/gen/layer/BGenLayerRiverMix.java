package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerRiverMix extends BGenLayer {
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
    
    public BGenLayerRiverMix(long seed, GenLayer biomeChain, GenLayer riverChain) {
        super(seed);
        this.biomePatternGeneratorChain = biomeChain;
        this.riverPatternGeneratorChain = riverChain;
    }
    
    public void initWorldGenSeed(long seed) {
        this.biomePatternGeneratorChain.initWorldGenSeed(seed);
        this.riverPatternGeneratorChain.initWorldGenSeed(seed);
        super.initWorldGenSeed(seed);
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] biome = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight, z);
        int[] river = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int i = 0; i < areaWidth * areaHeight; ++i) {
            if (biome[i] != BBiomes.ocean.biomeID && biome[i] != BBiomes.deepOcean.biomeID) {
                if (river[i] == BBiomes.river.biomeID) {
                    if (biome[i] == BBiomes.icePlains.biomeID) {
                        result[i] = BBiomes.frozenRiver.biomeID;
                    } else if (biome[i] != BBiomes.mushroomIsland.biomeID && biome[i] != BBiomes.mushroomIslandShore.biomeID) {
                        result[i] = river[i] & 255;
                    } else {
                        result[i] = BBiomes.mushroomIslandShore.biomeID;
                    }
                } else {
                    result[i] = biome[i];
                }
            } else {
                result[i] = biome[i];
            }
        }
        
        return result;
    }
}
