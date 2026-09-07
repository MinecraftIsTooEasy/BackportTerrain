package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public abstract class BWorldGenHugeTrees extends BWorldGenAbstractTree {
	protected final int baseHeight;
	protected final int extraRandomHeight;
	protected final int woodMetadata;
	protected final int leavesMetadata;

	public BWorldGenHugeTrees(boolean notify, int baseHeight, int extraRandomHeight, int woodMetadata, int leavesMetadata) {
		super(notify);
		this.baseHeight = baseHeight;
		this.extraRandomHeight = extraRandomHeight;
		this.woodMetadata = woodMetadata;
		this.leavesMetadata = leavesMetadata;
	}

	protected int func_150533_a(Random rand) {
		int i = rand.nextInt(3) + this.baseHeight;

		if (this.extraRandomHeight > 1) {
			i += rand.nextInt(this.extraRandomHeight);
		}

		return i;
	}

	private boolean func_150536_b(World world, Random rand, int x, int y, int z, int height) {
		boolean flag = true;

		if (y >= 1 && y + height + 1 <= 256) {
			for (int i1 = y; i1 <= y + 1 + height; ++i1) {
				int b0 = 2;

				if (i1 == y) {
					b0 = 1;
				}

				if (i1 >= y + 1 + height - 2) {
					b0 = 2;
				}

				for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
					for (int k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							if (!this.isReplaceable(world, j1, i1, k1)) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			return flag;
		} else {
			return false;
		}
	}

	private boolean func_150532_c(World world, Random rand, int x, int y, int z) {
		int soilId = world.getBlockId(x, y - 1, z);
		boolean isSoil = soilId == Block.grass.blockID || soilId == Block.dirt.blockID;

		return isSoil && y >= 2;
	}

	protected boolean func_150537_a(World world, Random rand, int x, int y, int z, int height) {
		return this.func_150536_b(world, rand, x, y, z, height) && this.func_150532_c(world, rand, x, y, z);
	}

	protected void func_150535_a(World world, int x, int y, int z, int radius, Random rand) {
		int i1 = radius * radius;

		for (int j1 = x - radius; j1 <= x + radius + 1; ++j1) {
			int k1 = j1 - x;

			for (int l1 = z - radius; l1 <= z + radius + 1; ++l1) {
				int i2 = l1 - z;
				int j2 = k1 - 1;
				int k2 = i2 - 1;

				if (k1 * k1 + i2 * i2 <= i1 || j2 * j2 + k2 * k2 <= i1 || k1 * k1 + k2 * k2 <= i1 || j2 * j2 + i2 * i2 <= i1) {
					int id = world.getBlockId(j1, y, l1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, j1, y, l1, Block.leaves.blockID, this.leavesMetadata);
					}
				}
			}
		}
	}

	protected void func_150534_b(World world, int x, int y, int z, int radius, Random rand) {
		int i1 = radius * radius;

		for (int j1 = x - radius; j1 <= x + radius; ++j1) {
			int k1 = j1 - x;

			for (int l1 = z - radius; l1 <= z + radius; ++l1) {
				int i2 = l1 - z;

				if (k1 * k1 + i2 * i2 <= i1) {
					int id = world.getBlockId(j1, y, l1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, j1, y, l1, Block.leaves.blockID, this.leavesMetadata);
					}
				}
			}
		}
	}
}
