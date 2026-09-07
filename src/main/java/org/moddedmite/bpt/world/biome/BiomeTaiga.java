package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityWolf;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;
import net.minecraft.WorldGenTallGrass;
import net.minecraft.WorldGenerator;
import org.moddedmite.bpt.world.gen.feature.BWorldGenMegaPineTree;
import org.moddedmite.bpt.world.gen.feature.BWorldGenTaiga1;
import org.moddedmite.bpt.world.gen.feature.BWorldGenTaiga2;

import java.util.Random;

public class BiomeTaiga extends BBiomes.BBiome {
    private final int type;

    public BiomeTaiga(int id, int type) {
        super(id);
        this.type = type;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;

        if (type != 1 && type != 2) {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.surface_mushrooms_per_chunk = 1;
        } else {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.surface_mushrooms_per_chunk = 3;
        }
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        if ((this.type == 1 || this.type == 2) && rand.nextInt(3) == 0) {
            return this.type != 2 && rand.nextInt(13) != 0
                ? new BWorldGenMegaPineTree(false, false)
                : new BWorldGenMegaPineTree(false, true);
        }
        return rand.nextInt(3) == 0 ? new BWorldGenTaiga1() : new BWorldGenTaiga2(false);
    }

    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        // TODO: mega taiga mossy boulder placement (WorldGenBlockBlob) unavailable in MITE
        // TODO: mega taiga tall fern placement (WorldGenDoublePlant) unavailable in MITE
        super.decorate(world, rand, chunkX, chunkZ);
    }

    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metadata, int x, int z, double depth) {
        if (this.type == 1 || this.type == 2) {
            this.topBlock = Block.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Block.dirt;
            // TODO: 1.7.10 uses coarse dirt (dirt meta 1) above ~1.75 and podzol (dirt meta 2) near the surface; both unavailable in MITE
        }

        this.genBiomeTerrain(world, rand, blocks, metadata, x, z, depth);
    }

    public BBiomes.BBiome createMutation() {
        if (this.biomeID == BBiomes.megaTaiga.biomeID) {
            BiomeTaiga mutation = new BiomeTaiga(this.biomeID + 128, 2);
            mutation.setColor(5858897, true);
            mutation.name("Mega Spruce Taiga");
            mutation.grassColor(5159473);
            mutation.tempRain(0.25F, 0.8F);
            mutation.height(new BBiomes.Height(this.minHeight, this.maxHeight));
            return mutation;
        }
        return super.createMutation();
    }
}
