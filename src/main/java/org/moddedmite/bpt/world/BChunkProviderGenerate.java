package org.moddedmite.bpt.world;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.BlockFalling;
import net.minecraft.Chunk;
import net.minecraft.ChunkProviderGenerate;
import net.minecraft.EnumCreatureType;
import net.minecraft.ExtendedBlockStorage;
import net.minecraft.IChunkProvider;
import net.minecraft.MathHelper;
import net.minecraft.SpawnerAnimals;
import net.minecraft.World;
import net.minecraft.WorldGenDungeons;
import net.minecraft.WorldGenLakes;
import org.moddedmite.bpt.api.IBiome;
import org.moddedmite.bpt.world.biome.BBiomes;
import org.moddedmite.bpt.world.gen.MapGenBase;
import org.moddedmite.bpt.world.gen.MapGenCaves;
import org.moddedmite.bpt.world.gen.MapGenRavine;
import org.moddedmite.bpt.world.gen.NoiseGeneratorPerlin;
import org.moddedmite.bpt.world.gen.structure.MapGenMineshaft;
import org.moddedmite.bpt.world.gen.structure.MapGenScatteredFeature;
import org.moddedmite.bpt.world.gen.structure.MapGenStronghold;
import org.moddedmite.bpt.world.gen.structure.MapGenVillage;

public class BChunkProviderGenerate extends ChunkProviderGenerate {
	private NoiseGeneratorPerlin stoneNoisePerlin;
	private double[] noiseField = new double[825];
	private float[] parabolicField = new float[25];
	private double[] noise1;
	private double[] noise2;
	private double[] noise3;
	private double[] noise6;
	protected World world;
	private MapGenBase caveGenerator = new MapGenCaves();
	private MapGenBase ravineGenerator = new MapGenRavine();
	private MapGenStronghold strongholdGenerator = new MapGenStronghold();
	private MapGenVillage villageGenerator = new MapGenVillage();
	private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
	private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();

	public BChunkProviderGenerate(World world, long seed, boolean mapFeaturesEnabled) {
		super(world, seed, mapFeaturesEnabled);
		this.world = world;
		this.stoneNoisePerlin = new NoiseGeneratorPerlin(this.rand, 4);

		for (int j = -2; j <= 2; ++j) {
			for (int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt_float((float) (j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}
	}

	public void generateTerrain(int chunkX, int chunkZ, Block[] blocks) {
		int seaLevel = 63;
		this.biomesForGeneration = this.world.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
		this.initializeNoiseField(chunkX * 4, 0, chunkZ * 4);

		for (int xz = 0; xz < 4; ++xz) {
			int l = xz * 5;
			int i1 = (xz + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = this.noiseField[k1 + k2];
					double d2 = this.noiseField[l1 + k2];
					double d3 = this.noiseField[i2 + k2];
					double d4 = this.noiseField[j2 + k2];
					double d5 = (this.noiseField[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.noiseField[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.noiseField[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.noiseField[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3) {
							int index = i3 + xz * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							int height = 256;
							index -= height;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0D) {
									blocks[index += height] = Block.stone;
								} else if (k2 * 8 + l2 < seaLevel) {
									blocks[index += height] = Block.waterStill;
								} else {
									blocks[index += height] = null;
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void initializeNoiseField(int startX, int startY, int startZ) {
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, startX, startZ, 5, 5, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, startX, startY, startZ, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, startX, startY, startZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, startX, startY, startZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int l = 0;
		int i1 = 0;

		for (int j1 = 0; j1 < 5; ++j1) {
			for (int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biome = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1) {
					for (int i2 = -b0; i2 <= b0; ++i2) {
						BiomeGenBase biome1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float rootHeight = biome1.minHeight;
						float heightVariation = biome1.maxHeight;
						float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (rootHeight + 2.0F);

						if (biome1.minHeight > biome.minHeight) {
							f5 /= 2.0F;
						}

						f += heightVariation * f5;
						f1 += rootHeight * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d12 = this.noise6[i1] / 8000.0D;

				if (d12 < 0.0D) {
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;

				if (d12 < 0.0D) {
					d12 /= 2.0D;

					if (d12 < -1.0D) {
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				} else {
					if (d12 > 1.0D) {
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double) f1;
				double d14 = (double) f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;

				for (int j2 = 0; j2 < 33; ++j2) {
					double d6 = ((double) j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if (d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = this.noise1[l] / 512.0D;
					double d8 = this.noise2[l] / 512.0D;
					double d9 = (this.noise3[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = (d9 < 0.0D ? d7 : (d9 > 1.0D ? d8 : d7 + (d8 - d7) * d9)) - d6;

					if (j2 > 29) {
						double d11 = (double) ((float) (j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					this.noiseField[l] = d10;
					++l;
				}
			}
		}
	}

	public void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] blocks, byte[] metadata, BiomeGenBase[] biomes) {
		double d0 = 0.03125D;
		this.stoneNoise = this.stoneNoisePerlin.func_151599_a(this.stoneNoise, (double) (chunkX * 16), (double) (chunkZ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int k = 0; k < 16; ++k) {
			for (int l = 0; l < 16; ++l) {
				BiomeGenBase biome = biomes[l + k * 16];
				((IBiome) biome).genTerrainBlocks(this.world, this.rand, blocks, metadata, chunkX * 16 + k, chunkZ * 16 + l, this.stoneNoise[l + k * 16]);
			}
		}
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		if (!this.world.isChunkWithinBlockDomain(chunkX, chunkZ)) {
			Chunk chunk = new Chunk(this.world, chunkX, chunkZ);
			chunk.generateSkylightMap(true);
			return chunk;
		}

		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
		Block[] blocks = new Block[65536];
		byte[] metadata = new byte[65536];
		this.generateTerrain(chunkX, chunkZ, blocks);
		this.biomesForGeneration = this.world.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		this.replaceBlocksForBiome(chunkX, chunkZ, blocks, metadata, this.biomesForGeneration);
		
		this.caveGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
		 this.ravineGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
		
		if (this.mapFeaturesEnabled) {
			this.mineshaftGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
			this.villageGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
			this.strongholdGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
			this.scatteredFeatureGenerator.generate(this, this.world, chunkX, chunkZ, blocks);
		}
		Chunk chunk = new Chunk(this.world, chunkX, chunkZ);

		for (int y = 0; y < 256; ++y) {
			int storageIndex = y >> 4;

			for (int z = 0; z < 16; ++z) {
				for (int x = 0; x < 16; ++x) {
					int index = (x * 16 + z) * 256 + y;
					Block block = blocks[index];

					if (block == null) {
						continue;
					}

					ExtendedBlockStorage storage = chunk.storageArrays[storageIndex];

					if (storage == null) {
						storage = chunk.storageArrays[storageIndex] = new ExtendedBlockStorage(storageIndex << 4, !this.world.provider.hasNoSky);
					}

					storage.setExtBlockID(x, y & 15, z, block.blockID);
					storage.setExtBlockMetadata(x, y & 15, z, metadata[index]);
				}
			}
		}

		byte[] biomeArray = chunk.getBiomeArray();

		for (int k = 0; k < biomeArray.length; ++k) {
			biomeArray[k] = (byte) this.biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap(true);
		return chunk;
	}

	@Override
	public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
		if (this.world.decorating) {
			return;
		}
		BlockFalling.fallInstantly = true;
		int x = chunkX * 16;
		int z = chunkZ * 16;
		BiomeGenBase biome = this.world.getBiomeGenForCoords(x + 16, z + 16);
		this.rand.setSeed(this.world.getSeed());
		long l1 = this.rand.nextLong() / 2L * 2L + 1L;
		long l2 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ this.world.getSeed());
		boolean flag = false;

		if (this.mapFeaturesEnabled) {
			this.mineshaftGenerator.generateStructuresInChunk(this.world, this.rand, chunkX, chunkZ);
			flag = this.villageGenerator.generateStructuresInChunk(this.world, this.rand, chunkX, chunkZ);
			this.strongholdGenerator.generateStructuresInChunk(this.world, this.rand, chunkX, chunkZ);
			this.scatteredFeatureGenerator.generateStructuresInChunk(this.world, this.rand, chunkX, chunkZ);
		}

		for (int i = 0; i < 4; ++i) {
			if (flag || this.rand.nextInt(6) != 0) {
				continue;
			}

			int x1 = x + this.rand.nextInt(16) + 8;
			int y1 = this.rand.nextInt(256);
			int z1 = z + this.rand.nextInt(16) + 8;

			for (int j = 1; j < i; ++j) {
				if (y1 <= 16) {
					continue;
				}

				y1 = this.rand.nextInt(y1);
			}

			int liquidId;

			if (Math.random() * 32.0 >= (double) (y1 - 16)) {
				liquidId = this.rand.nextInt(20) == 0 ? Block.waterStill.blockID : Block.lavaStill.blockID;
			} else {
				if (biome == BBiomes.desert || biome == BBiomes.desertHills) {
					continue;
				}

				liquidId = Block.waterStill.blockID;
			}

			new WorldGenLakes(liquidId).generate(this.world, this.rand, x1, y1, z1);
		}

		for (int i = 0; i < 8; ++i) {
			int x1 = x + this.rand.nextInt(16) + 8;
			int y1 = this.rand.nextInt(256);
			int z1 = z + this.rand.nextInt(16) + 8;
			new WorldGenDungeons().generate(this.world, this.rand, x1, y1, z1);
		}

		biome.decorate(this.world, this.rand, x, z);
		x += 8;
		z += 8;

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				int y = this.world.getPrecipitationHeight(x + i, z + j);

				if (this.world.isBlockFreezable(i + x, y - 1, j + z)) {
					this.world.setBlock(i + x, y - 1, j + z, Block.ice.blockID, 0, 2);
				}

				if (y > 63 && this.world.isAirBlock(i + x, 63, j + z) && this.world.isBlockFreezable(i + x, 62, j + z)) {
					this.world.setBlock(i + x, 62, j + z, Block.ice.blockID, 0, 2);
				}

				if (this.world.canSnowAt(i + x, y, j + z)) {
					this.world.setBlock(i + x, y, j + z, Block.snow.blockID, 0, 2);
				}
			}
		}

		SpawnerAnimals.performWorldGenSpawning(this.world, biome, EnumCreatureType.animal, x, z, 16, 16, this.rand);
		SpawnerAnimals.performWorldGenSpawning(this.world, biome, EnumCreatureType.aquatic, x, z, 16, 16, this.rand);
		BlockFalling.fallInstantly = false;
	}
}
