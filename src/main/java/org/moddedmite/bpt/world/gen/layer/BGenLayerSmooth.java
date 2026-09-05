package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerSmooth extends BGenLayer {
    public BGenLayerSmooth(long seed, GenLayer parent) {
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
                int left = parent[x + 0 + (y + 1) * k];
                int right = parent[x + 2 + (y + 1) * k];
                int up = parent[x + 1 + (y + 0) * k];
                int down = parent[x + 1 + (y + 2) * k];
                int center = parent[x + 1 + (y + 1) * k];
                
                if (left == right && up == down) {
                    this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                    
                    if (this.nextInt(2) == 0) {
                        center = left;
                    } else {
                        center = up;
                    }
                } else {
                    if (left == right) {
                        center = left;
                    }
                    
                    if (up == down) {
                        center = up;
                    }
                }
                
                result[x + y * areaWidth] = center;
            }
        }
        
        return result;
    }
}
