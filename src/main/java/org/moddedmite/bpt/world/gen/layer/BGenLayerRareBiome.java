package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerRareBiome extends BGenLayer {
    public BGenLayerRareBiome(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] parent = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        int stride = areaWidth + 2;
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int current = parent[x + 1 + (y + 1) * stride];
                
                if (this.nextInt(57) == 0) {
                    if (current == BBiomes.plains.biomeID && BiomeGenBase.biomeList[current + 128] != null) {
                        result[x + y * areaWidth] = current + 128;
                    } else {
                        result[x + y * areaWidth] = current;
                    }
                } else {
                    result[x + y * areaWidth] = current;
                }
            }
        }
        
        return result;
    }
}
