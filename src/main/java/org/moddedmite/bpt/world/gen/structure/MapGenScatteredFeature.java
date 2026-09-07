package org.moddedmite.bpt.world.gen.structure;

import net.minecraft.BiomeGenBase;
import net.minecraft.ComponentScatteredFeatureDesertPyramid;
import net.minecraft.ComponentScatteredFeatureJunglePyramid;
import net.minecraft.ComponentScatteredFeatureSwampHut;
import net.minecraft.EntityWitch;
import net.minecraft.MapGenStructureIO;
import net.minecraft.MathHelper;
import net.minecraft.SpawnListEntry;
import net.minecraft.StructureComponent;
import net.minecraft.StructureStart;
import net.minecraft.World;
import org.moddedmite.bpt.world.biome.BBiomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenScatteredFeature extends MapGenStructure {
    static {
        MapGenStructureIO.func_143034_b(MapGenScatteredFeature.Start.class, "Temple");
    }

    private static List biomelist = Arrays.asList(BBiomes.desert, BBiomes.desertHills, BBiomes.jungle, BBiomes.jungleHills, BBiomes.swampland);
    /**
     * contains possible spawns for scattered features
     */
    private List scatteredFeatureSpawnList;
    /**
     * the maximum distance between scattered features
     */
    private int maxDistanceBetweenScatteredFeatures;
    /**
     * the minimum distance between scattered features
     */
    private int minDistanceBetweenScatteredFeatures;

    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList = new ArrayList();
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.scatteredFeatureSpawnList.add(new SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    public MapGenScatteredFeature(Map p_i2061_1_) {
        this();

	    for (Object o : p_i2061_1_.entrySet()) {
		    Entry entry = (Entry) o;

		    if (entry.getKey().equals("distance")) {
			    this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
		    }
	    }
    }

    public String func_143025_a() {
        return "Temple";
    }

    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
        int k = p_75047_1_;
        int l = p_75047_2_;

        if (p_75047_1_ < 0) {
            p_75047_1_ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        if (p_75047_2_ < 0) {
            p_75047_2_ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        int i1 = p_75047_1_ / this.maxDistanceBetweenScatteredFeatures;
        int j1 = p_75047_2_ / this.maxDistanceBetweenScatteredFeatures;
        Random random = this.world.setRandomSeed(i1, j1, 14357617);
        i1 *= this.maxDistanceBetweenScatteredFeatures;
        j1 *= this.maxDistanceBetweenScatteredFeatures;
        i1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        j1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);

        if (k == i1 && l == j1) {
            BiomeGenBase biomegenbase = this.world.getWorldChunkManager().getBiomeGenAt(k * 16 + 8, l * 16 + 8);
            Iterator iterator = biomelist.iterator();

            while (iterator.hasNext()) {
                BiomeGenBase biomegenbase1 = (BiomeGenBase) iterator.next();

                if (biomegenbase == biomegenbase1) {
                    return true;
                }
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_) {
        return new MapGenScatteredFeature.Start(this.world, this.rand, p_75049_1_, p_75049_2_);
    }

    public boolean func_143030_a(int p_143030_1_, int p_143030_2_, int p_143030_3_) {
        StructureStart structurestart = this.func_143028_c(p_143030_1_, p_143030_2_, p_143030_3_);

        if (structurestart != null && structurestart instanceof MapGenScatteredFeature.Start && !structurestart.getComponents().isEmpty()) {
            StructureComponent structurecomponent = (StructureComponent) structurestart.getComponents().getFirst();
            return structurecomponent instanceof ComponentScatteredFeatureSwampHut;
        } else {
            return false;
        }
    }

    /**
     * returns possible spawns for scattered features
     */
    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }

    public static class Start extends StructureStart {
        public Start() {}

        public Start(World world, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            BiomeGenBase biomegenbase = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);

            if (biomegenbase != BBiomes.jungle && biomegenbase != BBiomes.jungleHills) {
                if (biomegenbase == BBiomes.swampland) {
                    ComponentScatteredFeatureSwampHut swamphut = new ComponentScatteredFeatureSwampHut(random, chunkX * 16, chunkZ * 16);
                    this.components.add(swamphut);
                } else {
                    ComponentScatteredFeatureDesertPyramid desertpyramid = new ComponentScatteredFeatureDesertPyramid(random, chunkX * 16, chunkZ * 16);
                    this.components.add(desertpyramid);
                }
            } else {
                ComponentScatteredFeatureJunglePyramid junglepyramid = new ComponentScatteredFeatureJunglePyramid(random, chunkX * 16, chunkZ * 16);
                this.components.add(junglepyramid);
            }

            this.updateBoundingBox();
        }
    }
}
