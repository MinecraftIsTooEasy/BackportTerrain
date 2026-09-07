package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenForest extends BWorldGenAbstractTree {
	private boolean tall;

	public BWorldGenForest(boolean notify, boolean tall) {
		super(notify);
		this.tall = tall;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(3) + 5;

		if (this.tall) {
			l += rand.nextInt(7);
		}

		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			int j1;
			int k1;

			for (int i1 = y; i1 <= y + 1 + l; ++i1) {
				int b0 = 1;

				if (i1 == y) {
					b0 = 0;
				}

				if (i1 >= y + 1 + l - 2) {
					b0 = 2;
				}

				for (j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
					for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
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

			if (!flag) {
				return false;
			} else {
				int soilId = world.getBlockId(x, y - 1, z);
				boolean isSoil = soilId == Block.grass.blockID || soilId == Block.dirt.blockID;

				if (isSoil && y < 256 - l - 1) {
					this.setBlock(world, x, y - 1, z, Block.dirt.blockID);
					int k2;

					for (k2 = y - 3 + l; k2 <= y + l; ++k2) {
						j1 = k2 - (y + l);
						k1 = 1 - j1 / 2;

						for (int l2 = x - k1; l2 <= x + k1; ++l2) {
							int l1 = l2 - x;

							for (int i2 = z - k1; i2 <= z + k1; ++i2) {
								int j2 = i2 - z;

								if (Math.abs(l1) != k1 || Math.abs(j2) != k1 || rand.nextInt(2) != 0 && j1 != 0) {
									int id = world.getBlockId(l2, k2, i2);

									if (id == 0 || id == Block.leaves.blockID) {
										this.setBlockAndMetadata(world, l2, k2, i2, Block.leaves.blockID, 2);
									}
								}
							}
						}
					}

					for (k2 = 0; k2 < l; ++k2) {
						int id = world.getBlockId(x, y + k2, z);

						if (id == 0 || id == Block.leaves.blockID) {
							this.setBlockAndMetadata(world, x, y + k2, z, Block.wood.blockID, 2);
						}
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}
