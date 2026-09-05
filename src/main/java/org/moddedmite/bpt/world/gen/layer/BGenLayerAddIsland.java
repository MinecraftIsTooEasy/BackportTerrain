package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerAddIsland extends BGenLayer {
    public BGenLayerAddIsland(long seed, GenLayer parent) {
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
                
                if (center == 0 && (left != 0 || right != 0 || up != 0 || down != 0)) {
                    int count = 1;
                    int value = 1;
                    
                    if (left != 0 && this.nextInt(count++) == 0) {
                        value = left;
                    }
                    
                    if (right != 0 && this.nextInt(count++) == 0) {
                        value = right;
                    }
                    
                    if (up != 0 && this.nextInt(count++) == 0) {
                        value = up;
                    }
                    
                    if (down != 0 && this.nextInt(count++) == 0) {
                        value = down;
                    }
                    
                    if (this.nextInt(3) == 0) {
                        result[x + y * areaWidth] = value;
                    } else if (value == 4) {
                        result[x + y * areaWidth] = 4;
                    } else {
                        result[x + y * areaWidth] = 0;
                    }
                } else if (center > 0 && (left == 0 || right == 0 || up == 0 || down == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (center == 4) {
                            result[x + y * areaWidth] = 4;
                        } else {
                            result[x + y * areaWidth] = 0;
                        }
                    } else {
                        result[x + y * areaWidth] = center;
                    }
                } else {
                    result[x + y * areaWidth] = center;
                }
            }
        }
        
        return result;
    }
}
