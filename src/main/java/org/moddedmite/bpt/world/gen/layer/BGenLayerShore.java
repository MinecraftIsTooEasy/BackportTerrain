package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;

public class BGenLayerShore extends BGenLayer {
    public BGenLayerShore(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] parent = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        int stride = areaWidth + 2;
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int current = parent[x + 1 + (y + 1) * stride];
                BiomeGenBase biome = BiomeGenBase.biomeList[current];
                int up;
                int right;
                int left;
                int down;
                
                if (current == BBiomes.mushroomIsland.biomeID) {
                    up = parent[x + 1 + (y + 1 - 1) * stride];
                    right = parent[x + 1 + 1 + (y + 1) * stride];
                    left = parent[x + 1 - 1 + (y + 1) * stride];
                    down = parent[x + 1 + (y + 1 + 1) * stride];
                    
                    if (up != BBiomes.ocean.biomeID && right != BBiomes.ocean.biomeID && left != BBiomes.ocean.biomeID && down != BBiomes.ocean.biomeID) {
                        result[x + y * areaWidth] = current;
                    } else {
                        result[x + y * areaWidth] = BBiomes.mushroomIslandShore.biomeID;
                    }
                } else if (this.isJungle(current)) {
                    up = parent[x + 1 + (y + 1 - 1) * stride];
                    right = parent[x + 1 + 1 + (y + 1) * stride];
                    left = parent[x + 1 - 1 + (y + 1) * stride];
                    down = parent[x + 1 + (y + 1 + 1) * stride];
                    
                    if (this.isJungleEdge(up) && this.isJungleEdge(right) && this.isJungleEdge(left) && this.isJungleEdge(down)) {
                        if (!isBiomeOceanic(up) && !isBiomeOceanic(right) && !isBiomeOceanic(left) && !isBiomeOceanic(down)) {
                            result[x + y * areaWidth] = current;
                        } else {
                            result[x + y * areaWidth] = BBiomes.beach.biomeID;
                        }
                    } else {
                        result[x + y * areaWidth] = BBiomes.jungleEdge.biomeID;
                    }
                } else if (current != BBiomes.extremeHills.biomeID && current != BBiomes.extremeHillsPlus.biomeID && current != BBiomes.extremeHillsEdge.biomeID) {
                    if (biome != null && biome.isFreezing()) {
                        this.replaceIfOceanicEdge(parent, result, x, y, areaWidth, current, BBiomes.coldBeach.biomeID);
                    } else if (current != BBiomes.mesa.biomeID && current != BBiomes.mesaPlateau_F.biomeID) {
                        if (current != BBiomes.ocean.biomeID && current != BBiomes.deepOcean.biomeID && current != BBiomes.river.biomeID && current != BBiomes.swampland.biomeID) {
                            up = parent[x + 1 + (y + 1 - 1) * stride];
                            right = parent[x + 1 + 1 + (y + 1) * stride];
                            left = parent[x + 1 - 1 + (y + 1) * stride];
                            down = parent[x + 1 + (y + 1 + 1) * stride];
                            
                            if (!isBiomeOceanic(up) && !isBiomeOceanic(right) && !isBiomeOceanic(left) && !isBiomeOceanic(down)) {
                                result[x + y * areaWidth] = current;
                            } else {
                                result[x + y * areaWidth] = BBiomes.beach.biomeID;
                            }
                        } else {
                            result[x + y * areaWidth] = current;
                        }
                    } else {
                        up = parent[x + 1 + (y + 1 - 1) * stride];
                        right = parent[x + 1 + 1 + (y + 1) * stride];
                        left = parent[x + 1 - 1 + (y + 1) * stride];
                        down = parent[x + 1 + (y + 1 + 1) * stride];
                        
                        if (!isBiomeOceanic(up) && !isBiomeOceanic(right) && !isBiomeOceanic(left) && !isBiomeOceanic(down)) {
                            if (this.isMesa(up) && this.isMesa(right) && this.isMesa(left) && this.isMesa(down)) {
                                result[x + y * areaWidth] = current;
                            } else {
                                result[x + y * areaWidth] = BBiomes.desert.biomeID;
                            }
                        } else {
                            result[x + y * areaWidth] = current;
                        }
                    }
                } else {
                    this.replaceIfOceanicEdge(parent, result, x, y, areaWidth, current, BBiomes.stoneBeach.biomeID);
                }
            }
        }
        
        return result;
    }
    
    private void replaceIfOceanicEdge(int[] parent, int[] result, int x, int y, int areaWidth, int current, int replacement) {
        if (isBiomeOceanic(current)) {
            result[x + y * areaWidth] = current;
        } else {
            int stride = areaWidth + 2;
            int up = parent[x + 1 + (y + 1 - 1) * stride];
            int right = parent[x + 1 + 1 + (y + 1) * stride];
            int left = parent[x + 1 - 1 + (y + 1) * stride];
            int down = parent[x + 1 + (y + 1 + 1) * stride];
            
            if (!isBiomeOceanic(up) && !isBiomeOceanic(right) && !isBiomeOceanic(left) && !isBiomeOceanic(down)) {
                result[x + y * areaWidth] = current;
            } else {
                result[x + y * areaWidth] = replacement;
            }
        }
    }
    
    private boolean isJungle(int id) {
        return id == BBiomes.jungle.biomeID || id == BBiomes.jungleHills.biomeID || id == BBiomes.jungleEdge.biomeID;
    }
    
    private boolean isJungleEdge(int id) {
        return id == BBiomes.jungleEdge.biomeID || id == BBiomes.jungle.biomeID || id == BBiomes.jungleHills.biomeID || id == BBiomes.forest.biomeID || id == BBiomes.taiga.biomeID || isBiomeOceanic(id);
    }
    
    private boolean isMesa(int id) {
        return id == BBiomes.mesa.biomeID || id == BBiomes.mesaPlateau.biomeID || id == BBiomes.mesaPlateau_F.biomeID;
    }
}
