package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.MathHelper;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenMegaPineTree extends BWorldGenHugeTrees {
	private final boolean useBaseHeight;

	public BWorldGenMegaPineTree(boolean notify, boolean useBaseHeight) {
		super(notify, 13, 15, 1, 1);
		this.useBaseHeight = useBaseHeight;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = this.func_150533_a(rand);

		if (!this.func_150537_a(world, rand, x, y, z, l)) {
			return false;
		} else {
			this.func_150541_c(world, x, z, y + l, 0, rand);

			for (int i1 = 0; i1 < l; ++i1) {
				int id = world.getBlockId(x, y + i1, z);

				if (id == 0 || id == Block.leaves.blockID) {
					this.setBlockAndMetadata(world, x, y + i1, z, Block.wood.blockID, this.woodMetadata);
				}

				if (i1 < l - 1) {
					id = world.getBlockId(x + 1, y + i1, z);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x + 1, y + i1, z, Block.wood.blockID, this.woodMetadata);
					}

					id = world.getBlockId(x + 1, y + i1, z + 1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x + 1, y + i1, z + 1, Block.wood.blockID, this.woodMetadata);
					}

					id = world.getBlockId(x, y + i1, z + 1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x, y + i1, z + 1, Block.wood.blockID, this.woodMetadata);
					}
				}
			}

			return true;
		}
	}

	private void func_150541_c(World world, int x, int z, int topY, int radius, Random rand) {
		int i1 = rand.nextInt(5);

		if (this.useBaseHeight) {
			i1 += this.baseHeight;
		} else {
			i1 += 3;
		}

		int j1 = 0;

		for (int k1 = topY - i1; k1 <= topY; ++k1) {
			int l1 = topY - k1;
			int i2 = radius + MathHelper.floor_float((float) l1 / (float) i1 * 3.5F);
			this.func_150535_a(world, x, k1, z, i2 + (l1 > 0 && i2 == j1 && (k1 & 1) == 0 ? 1 : 0), rand);
			j1 = i2;
		}
	}

	public void func_150524_b(World world, Random rand, int x, int y, int z) {
		this.func_150539_c(world, rand, x - 1, y, z - 1);
		this.func_150539_c(world, rand, x + 2, y, z - 1);
		this.func_150539_c(world, rand, x - 1, y, z + 2);
		this.func_150539_c(world, rand, x + 2, y, z + 2);

		for (int l = 0; l < 5; ++l) {
			int i1 = rand.nextInt(64);
			int j1 = i1 % 8;
			int k1 = i1 / 8;

			if (j1 == 0 || j1 == 7 || k1 == 0 || k1 == 7) {
				this.func_150539_c(world, rand, x - 3 + j1, y, z - 3 + k1);
			}
		}
	}

	private void func_150539_c(World world, Random rand, int x, int y, int z) {
		for (int l = -2; l <= 2; ++l) {
			for (int i1 = -2; i1 <= 2; ++i1) {
				if (Math.abs(l) != 2 || Math.abs(i1) != 2) {
					this.func_150540_a(world, x + l, y, z + i1);
				}
			}
		}
	}

	private void func_150540_a(World world, int x, int y, int z) {
		for (int l = y + 2; l >= y - 3; --l) {
			int soilId = world.getBlockId(x, l, z);

			if (soilId == Block.grass.blockID || soilId == Block.dirt.blockID) {
				// TODO: MITE lacks podzol (dirt meta 2)
				break;
			}

			if (soilId == 0 && l < y) {
				break;
			}
		}
	}
}
