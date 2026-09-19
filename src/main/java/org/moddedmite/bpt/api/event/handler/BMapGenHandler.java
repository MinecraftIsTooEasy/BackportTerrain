package org.moddedmite.bpt.api.event.handler;

import moddedmite.rustedironcore.api.world.Dimension;
import net.minecraft.Block;
import net.minecraft.IChunkProvider;
import net.minecraft.World;
import org.moddedmite.bpt.world.gen.MapGenBase;
import org.moddedmite.bpt.world.gen.structure.MapGenStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Registry for map generators and structures that run inside the backport chunk provider.
 * <br>
 * Note: {@link MapGenBase} / {@link MapGenStructure} are the backport copies used by
 * {@link org.moddedmite.bpt.world.BChunkProviderGenerate}, not the vanilla classes handled by
 * {@code moddedmite.rustedironcore.api.event.handler.MapGenHandler}.
 */
public class BMapGenHandler {
    private final Map<Dimension, List<MapGenBase>> MAP_GEN_MAP = new HashMap<>();
    private final Map<Dimension, List<MapGenStructure>> STRUCTURE_MAP = new HashMap<>();

    public void register(Dimension dimension, MapGenBase mapGen) {
        MAP_GEN_MAP.computeIfAbsent(dimension, k -> new ArrayList<>())
                .add(mapGen);
    }

    public void registerStructure(Dimension dimension, MapGenStructure structure) {
        List<MapGenStructure> structures = STRUCTURE_MAP.computeIfAbsent(dimension, key -> new ArrayList<>());
        if (!structures.contains(structure)) {
            structures.add(structure);
        }
    }

    public void onChunkProvideMapGen(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ, Block[] blocks) {
        List<MapGenBase> list = MAP_GEN_MAP.get(dimension);
        if (list == null) return;
        list.forEach(x -> x.generate(chunkProvider, world, chunkX, chunkZ, blocks));
    }

    public void onChunkProvideStructures(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ, Block[] blocks) {
        List<MapGenStructure> list = STRUCTURE_MAP.get(dimension);
        if (list == null) return;
        list.forEach(x -> x.generate(chunkProvider, world, chunkX, chunkZ, blocks));
    }

    public void onRecreateStructures(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ) {
        List<MapGenStructure> list = this.STRUCTURE_MAP.get(dimension);
        if (list == null) return;
        list.forEach(x -> x.generate(chunkProvider, world, chunkX, chunkZ, null));
    }

    public void onChunkPopulateStructures(Dimension dimension, World world, Random rand, int chunkX, int chunkZ) {
        List<MapGenStructure> list = this.STRUCTURE_MAP.get(dimension);
        if (list == null) return;
        list.forEach(x -> x.generateStructuresInChunk(world, rand, chunkX, chunkZ));
    }
}
