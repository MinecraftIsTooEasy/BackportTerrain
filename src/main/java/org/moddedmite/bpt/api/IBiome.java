package org.moddedmite.bpt.api;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public interface IBiome {
	void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metadata, int worldX, int worldZ, double stoneNoise);
}
