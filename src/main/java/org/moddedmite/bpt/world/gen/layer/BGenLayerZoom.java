package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerZoom extends BGenLayer {
    public BGenLayerZoom(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int parentX = areaX >> 1;
        int parentY = areaY >> 1;
        int parentWidth = (areaWidth >> 1) + 2;
        int parentHeight = (areaHeight >> 1) + 2;
        int[] parent = this.parent.getInts(parentX, parentY, parentWidth, parentHeight, z);
        int resultWidth = parentWidth - 1 << 1;
        int resultHeight = parentHeight - 1 << 1;
        int[] result = IntCache.getIntCache(resultWidth * resultHeight);
        
        for (int y = 0; y < parentHeight - 1; ++y) {
            int index = (y << 1) * resultWidth;
            int x = 0;
            int topLeft = parent[x + 0 + (y + 0) * parentWidth];
            
            for (int bottomLeft = parent[x + 0 + (y + 1) * parentWidth]; x < parentWidth - 1; ++x) {
                this.initChunkSeed((long) (x + parentX << 1), (long) (y + parentY << 1));
                int topRight = parent[x + 1 + (y + 0) * parentWidth];
                int bottomRight = parent[x + 1 + (y + 1) * parentWidth];
                result[index] = topLeft;
                result[index++ + resultWidth] = this.selectRandom(new int[]{topLeft, bottomLeft});
                result[index] = this.selectRandom(new int[]{topLeft, topRight});
                result[index++ + resultWidth] = this.selectModeOrRandom(topLeft, topRight, bottomLeft, bottomRight);
                topLeft = topRight;
                bottomLeft = bottomRight;
            }
        }
        
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            System.arraycopy(result, (y + (areaY & 1)) * resultWidth + (areaX & 1), out, y * areaWidth, areaWidth);
        }
        
        return out;
    }
    
    public static GenLayer magnify(long seed, GenLayer parent, int count) {
        GenLayer layer = parent;
        
        for (int i = 0; i < count; ++i) {
            layer = new BGenLayerZoom(seed + (long) i, layer);
        }
        
        return layer;
    }
}
