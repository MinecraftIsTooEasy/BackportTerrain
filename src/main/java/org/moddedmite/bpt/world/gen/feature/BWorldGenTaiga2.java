package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenTaiga2 extends BWorldGenAbstractTree {
	public BWorldGenTaiga2(boolean notify) {
		super(notify);
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(4) + 6;
		int i1 = 1 + rand.nextInt(2);
		int j1 = l - i1;
		int k1 = 2 + rand.nextInt(2);
		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			int i2;
			int l3;

			for (int l1 = y; l1 <= y + 1 + l && flag; ++l1) {
				if (l1 - y < i1) {
					l3 = 0;
				} else {
					l3 = k1;
				}

				for (i2 = x - l3; i2 <= x + l3 && flag; ++i2) {
					for (int j2 = z - l3; j2 <= z + l3 && flag; ++j2) {
						if (l1 >= 0 && l1 < 256) {
							int id = world.getBlockId(i2, l1, j2);

							if (id != 0 && id != Block.leaves.blockID) {
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
					l3 = rand.nextInt(2);
					i2 = 1;
					int b0 = 0;
					int k2;
					int i4;

					for (i4 = 0; i4 <= j1; ++i4) {
						k2 = y + l - i4;

						for (int l2 = x - l3; l2 <= x + l3; ++l2) {
							int i3 = l2 - x;

							for (int j3 = z - l3; j3 <= z + l3; ++j3) {
								int k3 = j3 - z;

								if ((Math.abs(i3) != l3 || Math.abs(k3) != l3 || l3 <= 0) && !Block.opaqueCubeLookup[world.getBlockId(l2, k2, j3)]) {
									this.setBlockAndMetadata(world, l2, k2, j3, Block.leaves.blockID, 1);
								}
							}
						}

						if (l3 >= i2) {
							l3 = b0;
							b0 = 1;
							++i2;

							if (i2 > k1) {
								i2 = k1;
							}
						} else {
							++l3;
						}
					}

					i4 = rand.nextInt(3);

					for (k2 = 0; k2 < l - i4; ++k2) {
						int id = world.getBlockId(x, y + k2, z);

						if (id == 0 || id == Block.leaves.blockID) {
							this.setBlockAndMetadata(world, x, y + k2, z, Block.wood.blockID, 1);
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
