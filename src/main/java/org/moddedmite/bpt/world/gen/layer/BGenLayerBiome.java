package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import net.minecraft.IntCache;
import net.minecraft.WorldType;
import org.moddedmite.bpt.api.event.BPTHandler;
import org.moddedmite.bpt.world.biome.BBiomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BGenLayerBiome extends BGenLayer {
    private BiomeGenBase[] hotBiomes;
    private BiomeGenBase[] warmBiomes;
    private BiomeGenBase[] coolBiomes;
    private BiomeGenBase[] icyBiomes;
    
    public BGenLayerBiome(long seed, GenLayer parent, WorldType worldType) {
        super(seed);
        this.hotBiomes = new BiomeGenBase[]{BBiomes.desert, BBiomes.desert, BBiomes.desert, BBiomes.savanna, BBiomes.savanna, BBiomes.plains};
        this.warmBiomes = new BiomeGenBase[]{BBiomes.forest, BBiomes.roofedForest, BBiomes.extremeHills, BBiomes.plains, BBiomes.birchForest, BBiomes.swampland};
        this.coolBiomes = new BiomeGenBase[]{BBiomes.forest, BBiomes.extremeHills, BBiomes.taiga, BBiomes.plains};
        this.icyBiomes = new BiomeGenBase[]{BBiomes.icePlains, BBiomes.icePlains, BBiomes.icePlains, BBiomes.coldTaiga};
        this.parent = parent;
        
        if (worldType == WorldType.DEFAULT_1_1) {
            this.hotBiomes = new BiomeGenBase[]{BBiomes.desert, BBiomes.forest, BBiomes.extremeHills, BBiomes.swampland, BBiomes.plains, BBiomes.taiga};
        }

        List<BiomeGenBase> hot = new ArrayList<>(Arrays.asList(this.hotBiomes));
        List<BiomeGenBase> warm = new ArrayList<>(Arrays.asList(this.warmBiomes));
        List<BiomeGenBase> cool = new ArrayList<>(Arrays.asList(this.coolBiomes));
        List<BiomeGenBase> icy = new ArrayList<>(Arrays.asList(this.icyBiomes));
        BPTHandler.BiomeGenerate.onInitialBiomesModify(hot, warm, cool, icy);
        this.hotBiomes = hot.toArray(new BiomeGenBase[0]);
        this.warmBiomes = warm.toArray(new BiomeGenBase[0]);
        this.coolBiomes = cool.toArray(new BiomeGenBase[0]);
        this.icyBiomes = icy.toArray(new BiomeGenBase[0]);
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight, int z) {
        int[] parent = this.parent.getInts(areaX, areaY, areaWidth, areaHeight, z);
        int[] result = IntCache.getIntCache(areaWidth * areaHeight);
        
        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int climate = parent[x + y * areaWidth];
                int special = (climate & 3840) >> 8;
                climate &= -3841;
                
                if (climate == 0) {
                    result[x + y * areaWidth] = BBiomes.ocean.biomeID;
                } else if (climate == BBiomes.deepOcean.biomeID) {
                    result[x + y * areaWidth] = climate;
                } else if (climate == BBiomes.mushroomIsland.biomeID) {
                    result[x + y * areaWidth] = climate;
                } else if (climate == 1) {
                    if (special > 0) {
                        result[x + y * areaWidth] = this.nextInt(3) == 0 ? BBiomes.mesaPlateau.biomeID : BBiomes.mesaPlateau_F.biomeID;
                    } else {
                        result[x + y * areaWidth] = this.hotBiomes[this.nextInt(this.hotBiomes.length)].biomeID;
                    }
                } else if (climate == 2) {
                    if (special > 0) {
                        result[x + y * areaWidth] = BBiomes.jungle.biomeID;
                    } else {
                        result[x + y * areaWidth] = this.warmBiomes[this.nextInt(this.warmBiomes.length)].biomeID;
                    }
                } else if (climate == 3) {
                    if (special > 0) {
                        result[x + y * areaWidth] = BBiomes.megaTaiga.biomeID;
                    } else {
                        result[x + y * areaWidth] = this.coolBiomes[this.nextInt(this.coolBiomes.length)].biomeID;
                    }
                } else if (climate == 4) {
                    result[x + y * areaWidth] = this.icyBiomes[this.nextInt(this.icyBiomes.length)].biomeID;
                } else {
                    result[x + y * areaWidth] = BBiomes.mushroomIsland.biomeID;
                }
            }
        }
        
        return result;
    }
}
