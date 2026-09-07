package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenTaiga1 extends BWorldGenAbstractTree {
	public BWorldGenTaiga1() {
		super(false);
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(5) + 7;
		int i1 = l - rand.nextInt(2) - 3;
		int j1 = l - i1;
		int k1 = 1 + rand.nextInt(j1 + 1);
		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			int i2;
			int j2;
			int i3;

			for (int l1 = y; l1 <= y + 1 + l && flag; ++l1) {
				if (l1 - y < i1) {
					i3 = 0;
				} else {
					i3 = k1;
				}

				for (i2 = x - i3; i2 <= x + i3 && flag; ++i2) {
					for (j2 = z - i3; j2 <= z + i3 && flag; ++j2) {
						if (l1 >= 0 && l1 < 256) {
							if (!this.isReplaceable(world, i2, l1, j2)) {
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
					i3 = 0;

					for (i2 = y + l; i2 >= y + i1; --i2) {
						for (j2 = x - i3; j2 <= x + i3; ++j2) {
							int j3 = j2 - x;

							for (int k2 = z - i3; k2 <= z + i3; ++k2) {
								int l2 = k2 - z;

								if ((Math.abs(j3) != i3 || Math.abs(l2) != i3 || i3 <= 0) && !Block.opaqueCubeLookup[world.getBlockId(j2, i2, k2)]) {
									this.setBlockAndMetadata(world, j2, i2, k2, Block.leaves.blockID, 1);
								}
							}
						}

						if (i3 >= 1 && i2 == y + i1 + 1) {
							--i3;
						} else if (i3 < k1) {
							++i3;
						}
					}

					for (i2 = 0; i2 < l - 1; ++i2) {
						int id = world.getBlockId(x, y + i2, z);

						if (id == 0 || id == Block.leaves.blockID) {
							this.setBlockAndMetadata(world, x, y + i2, z, Block.wood.blockID, 1);
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
