package org.moddedmite.bpt.world;

import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import net.minecraft.World;
import net.minecraft.WorldChunkManager;
import net.minecraft.WorldType;
import org.moddedmite.bpt.api.event.BPTHandler;
import org.moddedmite.bpt.world.biome.BBiomeSpawnMirror;
import org.moddedmite.bpt.world.biome.BBiomes;
import org.moddedmite.bpt.world.gen.layer.BGenLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BWorldChunkManager extends WorldChunkManager {
    private final List<BiomeGenBase> biomesToSpawnIn;

    public BWorldChunkManager(long seed, WorldType worldType) {
        super();
        GenLayer[] layers = BGenLayer.initializeAllBiomeGenerators(seed, worldType);
        this.genBiomes = layers[0];
        this.biomeIndexLayer = layers[1];
        this.biomesToSpawnIn = new ArrayList<>(Arrays.asList(BBiomes.forest, BBiomes.plains, BBiomes.taiga, BBiomes.taigaHills, BBiomes.forestHills, BBiomes.jungle, BBiomes.jungleHills));
        BPTHandler.BiomeGenerate.onPlayerSpawnableRegister(this.biomesToSpawnIn);
        BBiomeSpawnMirror.apply();
    }

    public BWorldChunkManager(World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }

    @Override
    public List<BiomeGenBase> getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }
}
