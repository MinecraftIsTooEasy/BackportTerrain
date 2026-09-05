package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerEdge extends BGenLayer {
    private final Mode mode;
    
    public BGenLayerEdge(long seed, GenLayer parent, Mode mode) {
        super(seed);
        this.parent = parent;
        this.mode = mode;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        switch (this.mode) {
            case COOL_WARM:
                return this.getIntsCoolWarm(areaX, areaY, areaWidth, areaHeight, z);
            case HEAT_ICE:
                return this.getIntsHeatIce(areaX, areaY, areaWidth, areaHeight, z);
            case SPECIAL:
                return this.getIntsSpecial(areaX, areaY, areaWidth, areaHeight, z);
            default:
                return this.getIntsCoolWarm(areaX, areaY, areaWidth, areaHeight, z);
        }
    }
    
    private int[] getIntsCoolWarm(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = 1 + areaWidth + 1;
        int l = 1 + areaHeight + 1;
        int[] parent = this.parent.getInts(i, j, k, l, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int center = parent[x + 1 + (y + 1) * k];
                
                if (center == 1) {
                    int up = parent[x + 1 + (y + 1 - 1) * k];
                    int right = parent[x + 1 + 1 + (y + 1) * k];
                    int left = parent[x + 1 - 1 + (y + 1) * k];
                    int down = parent[x + 1 + (y + 1 + 1) * k];
                    boolean cold = up == 3 || right == 3 || left == 3 || down == 3;
                    boolean icy = up == 4 || right == 4 || left == 4 || down == 4;
                    
                    if (cold || icy) {
                        center = 2;
                    }
                }
                
                result[x + y * areaWidth] = center;
            }
        }
        
        return result;
    }
    
    private int[] getIntsHeatIce(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = 1 + areaWidth + 1;
        int l = 1 + areaHeight + 1;
        int[] parent = this.parent.getInts(i, j, k, l, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                int center = parent[x + 1 + (y + 1) * k];
                
                if (center == 4) {
                    int up = parent[x + 1 + (y + 1 - 1) * k];
                    int right = parent[x + 1 + 1 + (y + 1) * k];
                    int left = parent[x + 1 - 1 + (y + 1) * k];
                    int down = parent[x + 1 + (y + 1 + 1) * k];
                    boolean warm = up == 2 || right == 2 || left == 2 || down == 2;
                    boolean hot = up == 1 || right == 1 || left == 1 || down == 1;
                    
                    if (hot || warm) {
                        center = 3;
                    }
                }
                
                result[x + y * areaWidth] = center;
            }
        }
        
        return result;
    }
    
    private int[] getIntsSpecial(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] parent = this.parent.getInts(areaX, areaY, areaWidth, areaHeight, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int center = parent[x + y * areaWidth];
                
                if (center != 0 && this.nextInt(13) == 0) {
                    center |= 1 + this.nextInt(15) << 8 & 3840;
                }
                
                result[x + y * areaWidth] = center;
            }
        }
        
        return result;
    }
    
    public enum Mode {
        COOL_WARM,
        HEAT_ICE,
        SPECIAL
    }
}
