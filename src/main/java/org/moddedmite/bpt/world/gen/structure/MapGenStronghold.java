package org.moddedmite.bpt.world.gen.structure;

import net.minecraft.ComponentStrongholdStairs2;
import net.minecraft.MathHelper;
import net.minecraft.ChunkCoordIntPair;
import net.minecraft.ChunkPosition;
import net.minecraft.StructureStart;
import net.minecraft.StructureStrongholdStart;
import net.minecraft.BiomeGenBase;
import org.moddedmite.bpt.api.event.BPTHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenStronghold extends MapGenStructure {
    public final List field_151546_e;
    /**
     * is spawned false and set true once the defined BiomeGenBases were compared with the present ones
     */
    private boolean ranBiomeCheck;
    private ChunkCoordIntPair[] structureCoords;
    private double field_82671_h;
    private int field_82672_i;

    public MapGenStronghold() {
        this.structureCoords = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0D;
        this.field_82672_i = 3;
        this.field_151546_e = new ArrayList();
        BiomeGenBase[] abiomegenbase = BiomeGenBase.biomeList;
        int i = abiomegenbase.length;

	    for (BiomeGenBase biomegenbase : abiomegenbase) {
		    if (biomegenbase != null && biomegenbase.minHeight > 0.0F) {
			    this.field_151546_e.add(biomegenbase);
		    }
	    }
        BPTHandler.BiomeGenerate.onStrongholdAllowedRegister(this.field_151546_e);
    }

    public MapGenStronghold(Map p_i2068_1_) {
        this();
	    
	    for (Object o : p_i2068_1_.entrySet()) {
		    Entry entry = (Entry) o;
		    
		    if (entry.getKey().equals("distance")) {
			    this.field_82671_h = org.moddedmite.bpt.util.MathHelper.parseDoubleWithDefaultAndMax((String) entry.getValue(), this.field_82671_h, 1.0D);
		    } else if (entry.getKey().equals("count")) {
			    this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.structureCoords.length, 1)];
		    } else if (entry.getKey().equals("spread")) {
			    this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.field_82672_i, 1);
		    }
	    }
    }

    public String func_143025_a() {
        return "Stronghold";
    }

    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
        if (!this.ranBiomeCheck) {
            Random random = new Random();
            random.setSeed(this.world.getSeed());
            double d0 = random.nextDouble() * Math.PI * 2.0D;
            int l = 1;

            for (int i1 = 0; i1 < this.structureCoords.length; ++i1) {
                double d1 = (1.25D * (double) l + random.nextDouble()) * this.field_82671_h * (double) l;
                int j1 = (int) Math.round(Math.cos(d0) * d1);
                int k1 = (int) Math.round(Math.sin(d0) * d1);
                ChunkPosition chunkposition = this.world.getWorldChunkManager().findBiomePosition((j1 << 4) + 8, (k1 << 4) + 8, 112, this.field_151546_e, random);

                if (chunkposition != null) {
                    j1 = chunkposition.x >> 4;
                    k1 = chunkposition.z >> 4;
                }

                this.structureCoords[i1] = new ChunkCoordIntPair(j1, k1);
                d0 += (Math.PI * 2D) * (double) l / (double) this.field_82672_i;

                if (i1 == this.field_82672_i) {
                    l += 2 + random.nextInt(5);
                    this.field_82672_i += 1 + random.nextInt(2);
                }
            }

            this.ranBiomeCheck = true;
        }

        ChunkCoordIntPair[] achunkcoordintpair = this.structureCoords;
	    
	    for (ChunkCoordIntPair chunkcoordintpair : achunkcoordintpair) {
		    if (p_75047_1_ == chunkcoordintpair.chunkXPos && p_75047_2_ == chunkcoordintpair.chunkZPos) {
			    return true;
		    }
	    }

        return false;
    }

    /**
     * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List getCoordList() {
        ArrayList arraylist = new ArrayList();
        ChunkCoordIntPair[] achunkcoordintpair = this.structureCoords;
        int i = achunkcoordintpair.length;

	    for (ChunkCoordIntPair chunkcoordintpair : achunkcoordintpair) {
		    if (chunkcoordintpair != null) {
			    arraylist.add(chunkcoordintpair.getChunkPosition(64));
		    }
	    }

        return arraylist;
    }

    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_) {
        StructureStrongholdStart start;

        for (start = new StructureStrongholdStart(this.world, this.rand, p_75049_1_, p_75049_2_); start.getComponents().isEmpty() || ((ComponentStrongholdStairs2) start.getComponents().get(0)).strongholdPortalRoom == null; start = new StructureStrongholdStart(this.world, this.rand, p_75049_1_, p_75049_2_)) {
            ;
        }

        return start;
    }
}
