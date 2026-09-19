package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;
import org.moddedmite.bpt.api.event.BPTHandler;
import org.moddedmite.bpt.world.biome.BBiomes;

public class BGenLayerHills extends BGenLayer {
    private GenLayer riverLayer;
    
    public BGenLayerHills(long seed, GenLayer biomeLayer, GenLayer riverLayer) {
        super(seed);
        this.parent = biomeLayer;
        this.riverLayer = riverLayer;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] biome = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2, z);
        int[] river = this.riverLayer.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        int stride = areaWidth + 2;
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int current = biome[x + 1 + (y + 1) * stride];
                int riverValue = river[x + 1 + (y + 1) * stride];
                boolean flag = (riverValue - 2) % 29 == 0;
                
                if (current != 0 && riverValue >= 2 && (riverValue - 2) % 29 == 1 && current < 128) {
                    if (BiomeGenBase.biomeList[current + 128] != null) {
                        result[x + y * areaWidth] = current + 128;
                    } else {
                        result[x + y * areaWidth] = current;
                    }
                } else if (this.nextInt(3) != 0 && !flag) {
                    result[x + y * areaWidth] = current;
                } else {
                    int replacement = current;
                    
                    if (current == BBiomes.desert.biomeID) {
                        replacement = BBiomes.desertHills.biomeID;
                    } else if (current == BBiomes.forest.biomeID) {
                        replacement = BBiomes.forestHills.biomeID;
                    } else if (current == BBiomes.birchForest.biomeID) {
                        replacement = BBiomes.birchForestHills.biomeID;
                    } else if (current == BBiomes.roofedForest.biomeID) {
                        replacement = BBiomes.plains.biomeID;
                    } else if (current == BBiomes.taiga.biomeID) {
                        replacement = BBiomes.taigaHills.biomeID;
                    } else if (current == BBiomes.megaTaiga.biomeID) {
                        replacement = BBiomes.megaTaigaHills.biomeID;
                    } else if (current == BBiomes.coldTaiga.biomeID) {
                        replacement = BBiomes.coldTaigaHills.biomeID;
                    } else if (current == BBiomes.plains.biomeID) {
                        replacement = this.nextInt(3) == 0 ? BBiomes.forestHills.biomeID : BBiomes.forest.biomeID;
                    } else if (current == BBiomes.icePlains.biomeID) {
                        replacement = BBiomes.iceMountains.biomeID;
                    } else if (current == BBiomes.jungle.biomeID) {
                        replacement = BBiomes.jungleHills.biomeID;
                    } else if (current == BBiomes.ocean.biomeID) {
                        replacement = BBiomes.deepOcean.biomeID;
                    } else if (current == BBiomes.extremeHills.biomeID) {
                        replacement = BBiomes.extremeHillsPlus.biomeID;
                    } else if (current == BBiomes.savanna.biomeID) {
                        replacement = BBiomes.savannaPlateau.biomeID;
                    } else if (compareBiomesById(current, BBiomes.mesaPlateau_F.biomeID)) {
                        replacement = BBiomes.mesa.biomeID;
                    } else if (current == BBiomes.deepOcean.biomeID && this.nextInt(3) == 0) {
                        replacement = this.nextInt(2) == 0 ? BBiomes.plains.biomeID : BBiomes.forest.biomeID;
                    }

                    int hillsOverride = BPTHandler.BiomeGenerate.onLayerHills(this, current);
                    if (hillsOverride != current) {
                        replacement = hillsOverride;
                    }

                    if (flag && replacement != current) {
                        if (BiomeGenBase.biomeList[replacement + 128] != null) {
                            replacement += 128;
                        } else {
                            replacement = current;
                        }
                    }
                    
                    if (replacement == current) {
                        result[x + y * areaWidth] = current;
                    } else {
                        int up = biome[x + 1 + (y + 1 - 1) * stride];
                        int right = biome[x + 1 + 1 + (y + 1) * stride];
                        int left = biome[x + 1 - 1 + (y + 1) * stride];
                        int down = biome[x + 1 + (y + 1 + 1) * stride];
                        int sameNeighbors = 0;
                        
                        if (compareBiomesById(up, current)) {
                            ++sameNeighbors;
                        }
                        
                        if (compareBiomesById(right, current)) {
                            ++sameNeighbors;
                        }
                        
                        if (compareBiomesById(left, current)) {
                            ++sameNeighbors;
                        }
                        
                        if (compareBiomesById(down, current)) {
                            ++sameNeighbors;
                        }
                        
                        result[x + y * areaWidth] = sameNeighbors >= 3 ? replacement : current;
                    }
                }
            }
        }
        
        return result;
    }
}
