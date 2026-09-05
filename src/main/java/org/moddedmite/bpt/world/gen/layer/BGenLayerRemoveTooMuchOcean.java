package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerRemoveTooMuchOcean extends BGenLayer {
    public BGenLayerRemoveTooMuchOcean(long seed, GenLayer parent) {
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
                result[x + y * areaWidth] = center;
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                
                if (center == 0 && up == 0 && right == 0 && left == 0 && down == 0 && this.nextInt(2) == 0) {
                    result[x + y * areaWidth] = 1;
                }
            }
        }
        
        return result;
    }
}
