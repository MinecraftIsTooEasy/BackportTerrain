package org.moddedmite.bpt.world;

import org.moddedmite.bpt.world.biome.BBiomes;
import org.moddedmite.bpt.world.gen.layer.BGenLayer;
import net.minecraft.GenLayer;
import net.minecraft.World;
import net.minecraft.WorldChunkManager;
import net.minecraft.WorldType;

import java.util.Arrays;
import java.util.List;

public class BWorldChunkManager extends WorldChunkManager {
    public BWorldChunkManager(long seed, WorldType worldType) {
        super();
        GenLayer[] layers = BGenLayer.initializeAllBiomeGenerators(seed, worldType);
        this.genBiomes = layers[0];
        this.biomeIndexLayer = layers[1];
    }
    
    public BWorldChunkManager(World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }
    
    @Override
    public List getBiomesToSpawnIn() {
        return Arrays.asList(BBiomes.forest, BBiomes.plains, BBiomes.taiga, BBiomes.taigaHills, BBiomes.forestHills, BBiomes.jungle, BBiomes.jungleHills);
    }
}
