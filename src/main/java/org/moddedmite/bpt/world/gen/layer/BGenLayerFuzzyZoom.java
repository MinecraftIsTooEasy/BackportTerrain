package org.moddedmite.bpt.world.gen.layer;

import net.minecraft.GenLayer;

public class BGenLayerFuzzyZoom extends BGenLayerZoom {
    public BGenLayerFuzzyZoom(long seed, GenLayer parent) {
        super(seed, parent);
    }
    
    protected int selectModeOrRandom(int a, int b, int c, int d) {
        return this.selectRandom(new int[]{a, b, c, d});
    }
}
