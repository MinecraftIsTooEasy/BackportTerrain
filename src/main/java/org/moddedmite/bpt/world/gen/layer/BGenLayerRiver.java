package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerRiver extends BGenLayer {
    public BGenLayerRiver(long seed, GenLayer parent) {
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
                int left = this.reduce(parent[x + 0 + (y + 1) * k]);
                int right = this.reduce(parent[x + 2 + (y + 1) * k]);
                int up = this.reduce(parent[x + 1 + (y + 0) * k]);
                int down = this.reduce(parent[x + 1 + (y + 2) * k]);
                int center = this.reduce(parent[x + 1 + (y + 1) * k]);
                
                if (center == left && center == up && center == right && center == down) {
                    result[x + y * areaWidth] = -1;
                } else {
                    result[x + y * areaWidth] = BBiomes.river.biomeID;
                }
            }
        }
        
        return result;
    }
    
    private int reduce(int value) {
        return value >= 2 ? 2 + (value & 1) : value;
    }
}
