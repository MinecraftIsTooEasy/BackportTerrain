package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerAddMushroomIsland extends BGenLayer {
    public BGenLayerAddMushroomIsland(long seed, GenLayer parent) {
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
                int left = parent[x + 0 + (y + 0) * k];
                int right = parent[x + 2 + (y + 0) * k];
                int up = parent[x + 0 + (y + 2) * k];
                int down = parent[x + 2 + (y + 2) * k];
                int center = parent[x + 1 + (y + 1) * k];
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                
                if (center == 0 && left == 0 && right == 0 && up == 0 && down == 0 && this.nextInt(100) == 0) {
                    result[x + y * areaWidth] = BBiomes.mushroomIsland.biomeID;
                } else {
                    result[x + y * areaWidth] = center;
                }
            }
        }
        
        return result;
    }
}
