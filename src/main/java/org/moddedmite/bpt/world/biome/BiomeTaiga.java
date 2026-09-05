package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.EntityWolf;
import net.minecraft.SpawnListEntry;
import net.minecraft.WorldGenTaiga1;
import net.minecraft.WorldGenTaiga2;
import net.minecraft.WorldGenTallGrass;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class BiomeTaiga extends BBiomes.BBiome {
    private static final WorldGenTaiga1 PINE_GENERATOR = new WorldGenTaiga1();
    private static final WorldGenTaiga2 SPRUCE_GENERATOR = new WorldGenTaiga2(false);
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
        // TODO: mega taiga pine/spruce (WorldGenMegaPineTree) is missing in MITE, fall back to normal pine/spruce
        return rand.nextInt(3) == 0 ? PINE_GENERATOR : SPRUCE_GENERATOR;
    }

    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
    }
}
