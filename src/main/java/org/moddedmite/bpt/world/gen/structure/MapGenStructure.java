package org.moddedmite.bpt.world.gen.structure;

import net.minecraft.Block;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.MapGenStructureData;
import net.minecraft.MapGenStructureIO;
import net.minecraft.NBTBase;
import net.minecraft.NBTTagCompound;
import net.minecraft.ReportedException;
import net.minecraft.ChunkCoordIntPair;
import net.minecraft.ChunkPosition;
import net.minecraft.StructureBoundingBox;
import net.minecraft.StructureComponent;
import net.minecraft.StructureStart;
import net.minecraft.World;
import org.moddedmite.bpt.world.gen.MapGenBase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MapGenStructure extends MapGenBase {
    private MapGenStructureData field_143029_e;
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Map structureMap = new HashMap();
    
    public abstract String func_143025_a();
    
    public final void recursiveGenerate(World worldIn, final int p_151538_2_, final int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {
        this.func_143027_a(worldIn);
        
        if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_)))) {
            this.rand.nextInt();
            
            try {
                if (this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_)) {
                    StructureStart structurestart = this.getStructureStart(p_151538_2_, p_151538_3_);
                    this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_)), structurestart);
                    this.func_143026_a(p_151538_2_, p_151538_3_, structurestart);
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.addCrashSectionCallable("Is feature chunk", () -> MapGenStructure.this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_) ? "True" : "False");
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[]{Integer.valueOf(p_151538_2_), Integer.valueOf(p_151538_3_)}));
                crashreportcategory.addCrashSectionCallable("Chunk pos hash", () -> String.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_)));
                crashreportcategory.addCrashSectionCallable("Structure type", () -> MapGenStructure.this.getClass().getCanonicalName());
                throw new ReportedException(crashreport);
            }
        }
    }
    
    /**
     * Generates structures in specified chunk next to existing structures. Does *not* generate StructureStarts.
     */
    public boolean generateStructuresInChunk(World p_75051_1_, Random p_75051_2_, int p_75051_3_, int p_75051_4_) {
        this.func_143027_a(p_75051_1_);
        int k = (p_75051_3_ << 4) + 8;
        int l = (p_75051_4_ << 4) + 8;
        boolean flag = false;

	    for (StructureStart structurestart : (Collection<StructureStart>) this.structureMap.values()) {

		    if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(k, l, k + 15, l + 15)) {
			    structurestart.generateStructure(p_75051_1_, p_75051_2_, new StructureBoundingBox(k, l, k + 15, l + 15));
			    flag = true;
			    this.func_143026_a(structurestart.func_143019_e(), structurestart.func_143018_f(), structurestart);
		    }
	    }

        return flag;
    }
    
    /**
     * Returns true if the structure generator has generated a structure located at the given position tuple.
     */
    public boolean hasStructureAt(int p_75048_1_, int p_75048_2_, int p_75048_3_) {
        this.func_143027_a(this.world);
        return this.func_143028_c(p_75048_1_, p_75048_2_, p_75048_3_) != null;
    }
    
    protected StructureStart func_143028_c(int p_143028_1_, int p_143028_2_, int p_143028_3_) {
	    
	    for (StructureStart structurestart : (Collection<StructureStart>) this.structureMap.values()) {
		    
		    if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(p_143028_1_, p_143028_3_, p_143028_1_, p_143028_3_)) {
			    
			    for (Object object : structurestart.getComponents()) {
				    StructureComponent structurecomponent = (StructureComponent) object;
				    
				    if (structurecomponent.getBoundingBox().isVecInside(p_143028_1_, p_143028_2_, p_143028_3_)) {
					    return structurestart;
				    }
			    }
		    }
	    }
        
        return null;
    }
    
    public boolean func_142038_b(int p_142038_1_, int p_142038_2_, int p_142038_3_) {
        this.func_143027_a(this.world);
        Iterator iterator = this.structureMap.values().iterator();
        StructureStart structurestart;
        
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            
            structurestart = (StructureStart) iterator.next();
        }
        while (!structurestart.isSizeableStructure());
        
        return structurestart.getBoundingBox().intersectsWith(p_142038_1_, p_142038_3_, p_142038_1_, p_142038_3_);
    }
    
    public ChunkPosition func_151545_a(World p_151545_1_, int p_151545_2_, int p_151545_3_, int p_151545_4_) {
        this.world = p_151545_1_;
        this.func_143027_a(p_151545_1_);
        this.rand.setSeed(p_151545_1_.getSeed());
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();
        long j1 = (long) (p_151545_2_ >> 4) * l;
        long k1 = (long) (p_151545_4_ >> 4) * i1;
        this.rand.setSeed(j1 ^ k1 ^ p_151545_1_.getSeed());
        this.recursiveGenerate(p_151545_1_, p_151545_2_ >> 4, p_151545_4_ >> 4, 0, 0, (Block[]) null);
        double d0 = Double.MAX_VALUE;
        ChunkPosition chunkposition = null;
        Iterator iterator = this.structureMap.values().iterator();
        ChunkPosition chunkposition1;
        int l1;
        int i2;
        int j2;
        double d1;
        
        while (iterator.hasNext()) {
            StructureStart structurestart = (StructureStart) iterator.next();
            
            if (structurestart.isSizeableStructure()) {
                StructureComponent structurecomponent = (StructureComponent) structurestart.getComponents().get(0);
                chunkposition1 = structurecomponent.getCenter();
                l1 = chunkposition1.x - p_151545_2_;
                i2 = chunkposition1.y - p_151545_3_;
                j2 = chunkposition1.z - p_151545_4_;
                d1 = (double) (l1 * l1 + i2 * i2 + j2 * j2);
                
                if (d1 < d0) {
                    d0 = d1;
                    chunkposition = chunkposition1;
                }
            }
        }
        
        if (chunkposition != null) {
            return chunkposition;
        } else {
            List list = this.getCoordList();
            
            if (list != null) {
                ChunkPosition chunkposition2 = null;
	            
	            for (Object o : list) {
		            chunkposition1 = (ChunkPosition) o;
		            l1 = chunkposition1.x - p_151545_2_;
		            i2 = chunkposition1.y - p_151545_3_;
		            j2 = chunkposition1.z - p_151545_4_;
		            d1 = (double) (l1 * l1 + i2 * i2 + j2 * j2);
		            
		            if (d1 < d0) {
			            d0 = d1;
			            chunkposition2 = chunkposition1;
		            }
	            }
                
                return chunkposition2;
            } else {
                return null;
            }
        }
    }
    
    /**
     * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List getCoordList() {
        return null;
    }
    
    private void func_143027_a(World world) {
        if (this.field_143029_e == null) {
            this.field_143029_e = (MapGenStructureData) world.mapStorage.loadData(MapGenStructureData.class, this.func_143025_a());
            
            if (this.field_143029_e == null) {
                this.field_143029_e = new MapGenStructureData(this.func_143025_a());
                world.mapStorage.setData(this.func_143025_a(), this.field_143029_e);
            } else {
                NBTTagCompound nbttagcompound = this.field_143029_e.func_143041_a();
	            
	            for (Object o : nbttagcompound.tagMap.keySet()) {
		            String s = (String) o;
		            NBTBase nbtbase = nbttagcompound.getTag(s);
		            
		            if (nbtbase.getId() == 10) {
			            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbtbase;
			            
			            if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
				            int i = nbttagcompound1.getInteger("ChunkX");
				            int j = nbttagcompound1.getInteger("ChunkZ");
				            StructureStart structurestart = MapGenStructureIO.func_143035_a(nbttagcompound1, world);
				            
				            if (structurestart != null) {
					            this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(i, j), structurestart);
				            }
			            }
		            }
	            }
            }
        }
    }
    
    private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart p_143026_3_) {
        this.field_143029_e.func_143043_a(p_143026_3_.func_143021_a(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
        this.field_143029_e.markDirty();
    }
    
    protected abstract boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_);
    
    protected abstract StructureStart getStructureStart(int p_75049_1_, int p_75049_2_);
}