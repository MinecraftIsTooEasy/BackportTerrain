package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenSwamp extends BWorldGenAbstractTree {
	public BWorldGenSwamp() {
		super(false);
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(4) + 5;

		while (world.getBlockId(x, y - 1, z) == Block.waterStill.blockID || world.getBlockId(x, y - 1, z) == Block.waterMoving.blockID) {
			--y;
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
					b0 = 3;
				}

				for (j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
					for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							int id = world.getBlockId(j1, i1, k1);

							if (id != 0 && id != Block.leaves.blockID) {
								if (id != Block.waterStill.blockID && id != Block.waterMoving.blockID) {
									flag = false;
								} else if (i1 > y) {
									flag = false;
								}
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
					int l1;
					int k2;
					int l2;

					for (k2 = y - 3 + l; k2 <= y + l; ++k2) {
						j1 = k2 - (y + l);
						k1 = 2 - j1 / 2;

						for (l2 = x - k1; l2 <= x + k1; ++l2) {
							l1 = l2 - x;

							for (int i2 = z - k1; i2 <= z + k1; ++i2) {
								int j2 = i2 - z;
								int leafId = world.getBlockId(l2, k2, i2);

								if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || rand.nextInt(2) != 0 && j1 != 0) && (leafId == 0 || leafId == Block.leaves.blockID)) {
									this.setBlockAndMetadata(world, l2, k2, i2, Block.leaves.blockID, 0);
								}
							}
						}
					}

					for (k2 = 0; k2 < l; ++k2) {
						int id = world.getBlockId(x, y + k2, z);

						if (id == 0 || id == Block.leaves.blockID || id == Block.waterMoving.blockID || id == Block.waterStill.blockID) {
							this.setBlockAndMetadata(world, x, y + k2, z, Block.wood.blockID, 0);
						}
					}

					for (k2 = y - 3 + l; k2 <= y + l; ++k2) {
						j1 = k2 - (y + l);
						k1 = 2 - j1 / 2;

						for (l2 = x - k1; l2 <= x + k1; ++l2) {
							for (l1 = z - k1; l1 <= z + k1; ++l1) {
								if (world.getBlockId(l2, k2, l1) == Block.leaves.blockID) {
									if (rand.nextInt(4) == 0 && world.getBlockId(l2 - 1, k2, l1) == 0) {
										this.generateVines(world, l2 - 1, k2, l1, 8);
									}

									if (rand.nextInt(4) == 0 && world.getBlockId(l2 + 1, k2, l1) == 0) {
										this.generateVines(world, l2 + 1, k2, l1, 2);
									}

									if (rand.nextInt(4) == 0 && world.getBlockId(l2, k2, l1 - 1) == 0) {
										this.generateVines(world, l2, k2, l1 - 1, 1);
									}

									if (rand.nextInt(4) == 0 && world.getBlockId(l2, k2, l1 + 1) == 0) {
										this.generateVines(world, l2, k2, l1 + 1, 4);
									}
								}
							}
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

	private void generateVines(World world, int x, int y, int z, int meta) {
		this.setBlockAndMetadata(world, x, y, z, Block.vine.blockID, meta);
		int i1 = 4;

		while (true) {
			--y;

			if (world.getBlockId(x, y, z) != 0 || i1 <= 0) {
				return;
			}

			this.setBlockAndMetadata(world, x, y, z, Block.vine.blockID, meta);
			--i1;
		}
	}
}
