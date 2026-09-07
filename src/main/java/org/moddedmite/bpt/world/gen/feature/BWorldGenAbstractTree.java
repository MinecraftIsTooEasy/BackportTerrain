package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenerator;

import java.util.Random;

public abstract class BWorldGenAbstractTree extends WorldGenerator {
	public BWorldGenAbstractTree(boolean notify) {
		super(notify);
	}

	protected boolean func_150523_a(int blockId) {
		return blockId == 0
			|| blockId == Block.leaves.blockID
			|| blockId == Block.grass.blockID
			|| blockId == Block.dirt.blockID
			|| blockId == Block.wood.blockID
			|| blockId == Block.sapling.blockID
			|| blockId == Block.vine.blockID;
	}

	public void func_150524_b(World world, Random rand, int x, int y, int z) {
	}

	protected boolean isReplaceable(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		return id == 0 || id == Block.leaves.blockID || id == Block.wood.blockID || this.func_150523_a(id);
	}
}
