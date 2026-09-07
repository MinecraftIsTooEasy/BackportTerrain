package org.moddedmite.bpt.world.gen;

import net.minecraft.Block;
import net.minecraft.IChunkProvider;
import net.minecraft.World;

import java.util.Random;

public class MapGenBase {
	protected int range = 8;
	protected Random rand = new Random();
	protected World world;
	
	public void generate(IChunkProvider provider, World world, int chunkX, int chunkZ, Block[] blocks) {
		int k = this.range;
		this.world = world;
		this.rand.setSeed(world.getSeed());
		long l = this.rand.nextLong();
		long i1 = this.rand.nextLong();
		
		for (int j1 = chunkX - k; j1 <= chunkX + k; ++j1) {
			for (int k1 = chunkZ - k; k1 <= chunkZ + k; ++k1) {
				long l1 = (long) j1 * l;
				long i2 = (long) k1 * i1;
				this.rand.setSeed(l1 ^ i2 ^ world.getSeed());
				this.recursiveGenerate(world, j1, k1, chunkX, chunkZ, blocks);
			}
		}
	}
	
	public void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, Block[] blocks) {
	}
}
