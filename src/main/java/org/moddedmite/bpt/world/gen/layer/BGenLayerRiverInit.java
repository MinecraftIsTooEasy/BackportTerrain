package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerRiverInit extends BGenLayer {
    public BGenLayerRiverInit(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] parent = this.parent.getInts(areaX, areaY, areaWidth, areaHeight, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                result[x + y * areaWidth] = parent[x + y * areaWidth] > 0 ? this.nextInt(299999) + 2 : 0;
            }
        }
        
        return result;
    }
}
