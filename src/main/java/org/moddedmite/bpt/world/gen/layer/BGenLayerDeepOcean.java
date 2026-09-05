package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerDeepOcean extends BGenLayer {
    public BGenLayerDeepOcean(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = areaWidth + 2;
        int l = areaHeight + 2;
        int[] parent = this.parent.getInts(i, j, k, l, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                int up = parent[x + 1 + (y + 1 - 1) * (areaWidth + 2)];
                int right = parent[x + 1 + 1 + (y + 1) * (areaWidth + 2)];
                int left = parent[x + 1 - 1 + (y + 1) * (areaWidth + 2)];
                int down = parent[x + 1 + (y + 1 + 1) * (areaWidth + 2)];
                int center = parent[x + 1 + (y + 1) * k];
                int oceanCount = 0;
                
                if (up == 0) {
                    ++oceanCount;
                }
                
                if (right == 0) {
                    ++oceanCount;
                }
                
                if (left == 0) {
                    ++oceanCount;
                }
                
                if (down == 0) {
                    ++oceanCount;
                }
                
                if (center == 0 && oceanCount > 3) {
                    result[x + y * areaWidth] = BBiomes.deepOcean.biomeID;
                } else {
                    result[x + y * areaWidth] = center;
                }
            }
        }
        
        return result;
    }
}
