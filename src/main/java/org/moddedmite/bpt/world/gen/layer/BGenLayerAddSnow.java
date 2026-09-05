package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerAddSnow extends BGenLayer {
    public BGenLayerAddSnow(long seed, GenLayer parent) {
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
                int center = parent[x + 1 + (y + 1) * k];
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                
                if (center == 0) {
                    result[x + y * areaWidth] = 0;
                } else {
                    int roll = this.nextInt(6);
                    int value;
                    
                    if (roll == 0) {
                        value = 4;
                    } else if (roll <= 1) {
                        value = 3;
                    } else {
                        value = 1;
                    }
                    
                    result[x + y * areaWidth] = value;
                }
            }
        }
        
        return result;
    }
}
