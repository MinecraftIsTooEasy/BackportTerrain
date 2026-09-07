package org.moddedmite.bpt.world.gen.structure;

import net.minecraft.MathHelper;
import net.minecraft.StructureMineshaftStart;
import net.minecraft.StructureStart;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapGenMineshaft extends MapGenStructure {
    private double field_82673_e = 0.004D;
    
    public MapGenMineshaft() {
    }
    
    public String func_143025_a() {
        return "Mineshaft";
    }
    
    public MapGenMineshaft(Map p_i2034_1_) {
	    
	    for (Entry entry : (Set<Entry>) p_i2034_1_.entrySet()) {
		    
		    if (entry.getKey().equals("chance")) {
			    this.field_82673_e = MathHelper.parseDoubleWithDefault((String) entry.getValue(), this.field_82673_e);
		    }
	    }
    }
    
    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_));
    }
    
    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_) {
        return new StructureMineshaftStart(this.world, this.rand, p_75049_1_, p_75049_2_);
    }
}