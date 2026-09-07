package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.MathHelper;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenMegaJungle extends BWorldGenHugeTrees {
	public BWorldGenMegaJungle(boolean notify, int baseHeight, int extraRandomHeight, int woodMetadata, int leavesMetadata) {
		super(notify, baseHeight, extraRandomHeight, woodMetadata, leavesMetadata);
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = this.func_150533_a(rand);

		if (!this.func_150537_a(world, rand, x, y, z, l)) {
			return false;
		} else {
			this.func_150543_c(world, x, z, y + l, 2, rand);

			for (int i1 = y + l - 2 - rand.nextInt(4); i1 > y + l / 2; i1 -= 2 + rand.nextInt(4)) {
				float f = rand.nextFloat() * (float) Math.PI * 2.0F;
				int j1 = x + (int) (0.5F + MathHelper.cos(f) * 4.0F);
				int k1 = z + (int) (0.5F + MathHelper.sin(f) * 4.0F);
				int l1;

				for (l1 = 0; l1 < 5; ++l1) {
					j1 = x + (int) (1.5F + MathHelper.cos(f) * (float) l1);
					k1 = z + (int) (1.5F + MathHelper.sin(f) * (float) l1);
					this.setBlockAndMetadata(world, j1, i1 - 3 + l1 / 2, k1, Block.wood.blockID, this.woodMetadata);
				}

				l1 = 1 + rand.nextInt(2);
				int i2 = i1;

				for (int j2 = i1 - l1; j2 <= i2; ++j2) {
					int k2 = j2 - i2;
					this.func_150534_b(world, j1, j2, k1, 1 - k2, rand);
				}
			}

			for (int l2 = 0; l2 < l; ++l2) {
				int id = world.getBlockId(x, y + l2, z);

				if (id == 0 || id == Block.leaves.blockID) {
					this.setBlockAndMetadata(world, x, y + l2, z, Block.wood.blockID, this.woodMetadata);

					if (l2 > 0) {
						if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + l2, z)) {
							this.setBlockAndMetadata(world, x - 1, y + l2, z, Block.vine.blockID, 8);
						}

						if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + l2, z - 1)) {
							this.setBlockAndMetadata(world, x, y + l2, z - 1, Block.vine.blockID, 1);
						}
					}
				}

				if (l2 < l - 1) {
					id = world.getBlockId(x + 1, y + l2, z);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x + 1, y + l2, z, Block.wood.blockID, this.woodMetadata);

						if (l2 > 0) {
							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 2, y + l2, z)) {
								this.setBlockAndMetadata(world, x + 2, y + l2, z, Block.vine.blockID, 2);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + l2, z - 1)) {
								this.setBlockAndMetadata(world, x + 1, y + l2, z - 1, Block.vine.blockID, 1);
							}
						}
					}

					id = world.getBlockId(x + 1, y + l2, z + 1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x + 1, y + l2, z + 1, Block.wood.blockID, this.woodMetadata);

						if (l2 > 0) {
							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 2, y + l2, z + 1)) {
								this.setBlockAndMetadata(world, x + 2, y + l2, z + 1, Block.vine.blockID, 2);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + l2, z + 2)) {
								this.setBlockAndMetadata(world, x + 1, y + l2, z + 2, Block.vine.blockID, 4);
							}
						}
					}

					id = world.getBlockId(x, y + l2, z + 1);

					if (id == 0 || id == Block.leaves.blockID) {
						this.setBlockAndMetadata(world, x, y + l2, z + 1, Block.wood.blockID, this.woodMetadata);

						if (l2 > 0) {
							if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + l2, z + 1)) {
								this.setBlockAndMetadata(world, x - 1, y + l2, z + 1, Block.vine.blockID, 8);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + l2, z + 2)) {
								this.setBlockAndMetadata(world, x, y + l2, z + 2, Block.vine.blockID, 4);
							}
						}
					}
				}
			}

			return true;
		}
	}

	private void func_150543_c(World world, int x, int z, int topY, int radius, Random rand) {
		int b0 = 2;

		for (int i1 = topY - b0; i1 <= topY; ++i1) {
			int j1 = i1 - topY;
			this.func_150535_a(world, x, i1, z, radius + 1 - j1, rand);
		}
	}
}
