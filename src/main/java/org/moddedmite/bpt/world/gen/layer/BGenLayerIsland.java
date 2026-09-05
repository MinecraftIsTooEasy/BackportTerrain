package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.IntCache;

public class BGenLayerIsland extends BGenLayer {
    public BGenLayerIsland(long seed) {
        super(seed);
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int i = 0; i < areaHeight; ++i) {
            for (int j = 0; j < areaWidth; ++j) {
                this.initChunkSeed((long) (areaX + j), (long) (areaY + i));
                result[j + i * areaWidth] = this.nextInt(10) == 0 ? 1 : 0;
            }
        }
        
        if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
            result[-areaX + -areaY * areaWidth] = 1;
        }
        
        return result;
    }
}
