package org.moddedmite.bpt.world.gen.feature;

import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

public class BWorldGenShrub extends BWorldGenTrees {
	private final int woodMeta;
	private final int leavesMeta;

	public BWorldGenShrub(int woodMeta, int leavesMeta) {
		super(false);
		this.woodMeta = woodMeta;
		this.leavesMeta = leavesMeta;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int id;

		do {
			id = world.getBlockId(x, y, z);
			if (id != 0 && id != Block.leaves.blockID) {
				break;
			}
			--y;
		} while (y > 0);

		int soilId = world.getBlockId(x, y, z);
		boolean isSoil = soilId == Block.grass.blockID || soilId == Block.dirt.blockID;

		if (isSoil) {
			++y;
			this.setBlockAndMetadata(world, x, y, z, Block.wood.blockID, this.woodMeta);

			for (int l = y; l <= y + 2; ++l) {
				int i1 = l - y;
				int j1 = 2 - i1;

				for (int k1 = x - j1; k1 <= x + j1; ++k1) {
					int l1 = k1 - x;

					for (int i2 = z - j1; i2 <= z + j1; ++i2) {
						int j2 = i2 - z;
						int leafId = world.getBlockId(k1, l, i2);

						if ((Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0) && (leafId == 0 || leafId == Block.leaves.blockID)) {
							this.setBlockAndMetadata(world, k1, l, i2, Block.leaves.blockID, this.leavesMeta);
						}
					}
				}
			}
		}

		return true;
	}
}
