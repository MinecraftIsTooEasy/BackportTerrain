package org.moddedmite.bpt.api;

import net.minecraft.BiomeGenBase;
import net.minecraft.World;

import java.util.Random;

public interface IBiomeDecorator {
	default void decorateChunk(World world, Random rand, BiomeGenBase biome, int chunkX, int chunkZ) {
	}
}
