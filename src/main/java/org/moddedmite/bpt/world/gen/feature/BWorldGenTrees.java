package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.Direction;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenTrees extends BWorldGenAbstractTree {
	private final int minTreeHeight;
	private final boolean vinesGrow;
	private final int metaWood;
	private final int metaLeaves;

	public BWorldGenTrees(boolean notify) {
		this(notify, 4, 0, 0, false);
	}

	public BWorldGenTrees(boolean notify, int minTreeHeight, int metaWood, int metaLeaves, boolean vinesGrow) {
		super(notify);
		this.minTreeHeight = minTreeHeight;
		this.metaWood = metaWood;
		this.metaLeaves = metaLeaves;
		this.vinesGrow = vinesGrow;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			int b0;
			int k1;
			int id;

			for (int i1 = y; i1 <= y + 1 + l; ++i1) {
				b0 = 1;

				if (i1 == y) {
					b0 = 0;
				}

				if (i1 >= y + 1 + l - 2) {
					b0 = 2;
				}

				for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
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
					b0 = 3;
					int b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = y - b0 + l; k1 <= y + l; ++k1) {
						i3 = k1 - (y + l);
						l1 = b1 + 1 - i3 / 2;

						for (i2 = x - l1; i2 <= x + l1; ++i2) {
							j2 = i2 - x;

							for (int k2 = z - l1; k2 <= z + l1; ++k2) {
								int l2 = k2 - z;

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									id = world.getBlockId(i2, k1, k2);

									if (id == 0 || id == Block.leaves.blockID) {
										this.setBlockAndMetadata(world, i2, k1, k2, Block.leaves.blockID, this.metaLeaves);
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < l; ++k1) {
						id = world.getBlockId(x, y + k1, z);

						if (id == 0 || id == Block.leaves.blockID) {
							this.setBlockAndMetadata(world, x, y + k1, z, Block.wood.blockID, this.metaWood);

							if (this.vinesGrow && k1 > 0) {
								if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + k1, z)) {
									this.setBlockAndMetadata(world, x - 1, y + k1, z, Block.vine.blockID, 8);
								}

								if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + k1, z)) {
									this.setBlockAndMetadata(world, x + 1, y + k1, z, Block.vine.blockID, 2);
								}

								if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z - 1)) {
									this.setBlockAndMetadata(world, x, y + k1, z - 1, Block.vine.blockID, 1);
								}

								if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z + 1)) {
									this.setBlockAndMetadata(world, x, y + k1, z + 1, Block.vine.blockID, 4);
								}
							}
						}
					}

					if (this.vinesGrow) {
						for (k1 = y - 3 + l; k1 <= y + l; ++k1) {
							i3 = k1 - (y + l);
							l1 = 2 - i3 / 2;

							for (i2 = x - l1; i2 <= x + l1; ++i2) {
								for (j2 = z - l1; j2 <= z + l1; ++j2) {
									if (world.getBlockId(i2, k1, j2) == Block.leaves.blockID) {
										if (rand.nextInt(4) == 0 && world.getBlockId(i2 - 1, k1, j2) == 0) {
											this.growVines(world, i2 - 1, k1, j2, 8);
										}

										if (rand.nextInt(4) == 0 && world.getBlockId(i2 + 1, k1, j2) == 0) {
											this.growVines(world, i2 + 1, k1, j2, 2);
										}

										if (rand.nextInt(4) == 0 && world.getBlockId(i2, k1, j2 - 1) == 0) {
											this.growVines(world, i2, k1, j2 - 1, 1);
										}

										if (rand.nextInt(4) == 0 && world.getBlockId(i2, k1, j2 + 1) == 0) {
											this.growVines(world, i2, k1, j2 + 1, 4);
										}
									}
								}
							}
						}

						if (rand.nextInt(5) == 0 && l > 5) {
							for (k1 = 0; k1 < 2; ++k1) {
								for (i3 = 0; i3 < 4; ++i3) {
									if (rand.nextInt(4 - k1) == 0) {
										l1 = rand.nextInt(3);
										this.setBlockAndMetadata(world, x + Direction.offsetX[Direction.rotateOpposite[i3]], y + l - 5 + k1, z + Direction.offsetZ[Direction.rotateOpposite[i3]], Block.cocoaPlant.blockID, l1 << 2 | i3);
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

	private void growVines(World world, int x, int y, int z, int meta) {
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
