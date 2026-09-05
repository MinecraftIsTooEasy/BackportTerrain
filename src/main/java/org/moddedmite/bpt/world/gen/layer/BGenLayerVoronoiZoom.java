package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerVoronoiZoom extends BGenLayer {
    public BGenLayerVoronoiZoom(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        areaX -= 2;
        areaY -= 2;
        int parentX = areaX >> 2;
        int parentY = areaY >> 2;
        int parentWidth = (areaWidth >> 2) + 2;
        int parentHeight = (areaHeight >> 2) + 2;
        int[] parent = this.parent.getInts(parentX, parentY, parentWidth, parentHeight, z);
        int resultWidth = parentWidth - 1 << 2;
        int resultHeight = parentHeight - 1 << 2;
        int[] result = IntCache.getIntCache(resultWidth * resultHeight);
        
        for (int y = 0; y < parentHeight - 1; ++y) {
            int x = 0;
            int topLeft = parent[x + 0 + (y + 0) * parentWidth];
            
            for (int topRight = parent[x + 0 + (y + 1) * parentWidth]; x < parentWidth - 1; ++x) {
                double d0 = 3.6D;
                this.initChunkSeed((long) (x + parentX << 2), (long) (y + parentY << 2));
                double d1 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
                double d2 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
                this.initChunkSeed((long) (x + parentX + 1 << 2), (long) (y + parentY << 2));
                double d3 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
                double d4 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
                this.initChunkSeed((long) (x + parentX << 2), (long) (y + parentY + 1 << 2));
                double d5 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
                double d6 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
                this.initChunkSeed((long) (x + parentX + 1 << 2), (long) (y + parentY + 1 << 2));
                double d7 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
                double d8 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
                int bottomLeft = parent[x + 1 + (y + 0) * parentWidth] & 255;
                int bottomRight = parent[x + 1 + (y + 1) * parentWidth] & 255;
                
                for (int i = 0; i < 4; ++i) {
                    int index = ((y << 2) + i) * resultWidth + (x << 2);
                    
                    for (int j = 0; j < 4; ++j) {
                        double d9 = ((double) i - d2) * ((double) i - d2) + ((double) j - d1) * ((double) j - d1);
                        double d10 = ((double) i - d4) * ((double) i - d4) + ((double) j - d3) * ((double) j - d3);
                        double d11 = ((double) i - d6) * ((double) i - d6) + ((double) j - d5) * ((double) j - d5);
                        double d12 = ((double) i - d8) * ((double) i - d8) + ((double) j - d7) * ((double) j - d7);
                        
                        if (d9 < d10 && d9 < d11 && d9 < d12) {
                            result[index++] = topLeft;
                        } else if (d10 < d9 && d10 < d11 && d10 < d12) {
                            result[index++] = bottomLeft;
                        } else if (d11 < d9 && d11 < d10 && d11 < d12) {
                            result[index++] = topRight;
                        } else {
                            result[index++] = bottomRight;
                        }
                    }
                }
                
                topLeft = bottomLeft;
                topRight = bottomRight;
            }
        }
        
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            System.arraycopy(result, (y + (areaY & 3)) * resultWidth + (areaX & 3), out, y * areaWidth, areaWidth);
        }
        
        return out;
    }
}
