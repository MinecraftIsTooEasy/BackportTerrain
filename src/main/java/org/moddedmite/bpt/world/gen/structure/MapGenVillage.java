package org.moddedmite.bpt.world.gen.structure;

import net.minecraft.MathHelper;
import net.minecraft.StructureStart;
import net.minecraft.StructureVillageStart;
import org.moddedmite.bpt.world.biome.BBiomes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenVillage extends MapGenStructure {
    /**
     * A list of all the biomes villages can spawn in.
     */
    public static List villageSpawnBiomes = Arrays.asList(BBiomes.plains, BBiomes.desert, BBiomes.savanna);
    /**
     * World terrain type, 0 for normal, 1 for flat map
     */
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;

    public MapGenVillage() {
        this.field_82665_g = 32;
        this.field_82666_h = 8;
    }

    public MapGenVillage(Map p_i2093_1_) {
        this();

	    for (Object o : p_i2093_1_.entrySet()) {
		    Entry entry = (Entry) o;

		    if (entry.getKey().equals("size")) {
			    this.terrainType = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.terrainType, 0);
		    } else if (entry.getKey().equals("distance")) {
			    this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
		    }
	    }
    }

    public String func_143025_a() {
        return "Village";
    }

    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
        int k = p_75047_1_;
        int l = p_75047_2_;

        if (p_75047_1_ < 0) {
            p_75047_1_ -= this.field_82665_g - 1;
        }

        if (p_75047_2_ < 0) {
            p_75047_2_ -= this.field_82665_g - 1;
        }

        int i1 = p_75047_1_ / this.field_82665_g;
        int j1 = p_75047_2_ / this.field_82665_g;
        Random random = this.world.setRandomSeed(i1, j1, 10387312);
        i1 *= this.field_82665_g;
        j1 *= this.field_82665_g;
        i1 += random.nextInt(this.field_82665_g - this.field_82666_h);
        j1 += random.nextInt(this.field_82665_g - this.field_82666_h);

        if (k == i1 && l == j1) {
	        return this.world.getWorldChunkManager().areBiomesViable(k * 16 + 8, l * 16 + 8, 0, villageSpawnBiomes);
        }

        return false;
    }

    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_) {
        return new StructureVillageStart(this.world, this.rand, p_75049_1_, p_75049_2_, this.terrainType);
    }
}
