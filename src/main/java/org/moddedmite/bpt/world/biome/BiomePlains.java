package org.moddedmite.bpt.world.biome;

import net.minecraft.EntityHorse;
import net.minecraft.SpawnListEntry;
import net.minecraft.World;

import java.util.Random;

public class BiomePlains extends BBiomes.BBiome {
    private boolean sunflower;

    public BiomePlains(int id) {
        super(id);
        this.tempRain(0.8F, 0.4F);
        this.height(BBiomes.height_LowPlains);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
        // TODO: 1.7.10 plains flower distribution (BlockFlower red/yellow variants) unavailable in MITE
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        double d0 = BBiomes.plantNoise.func_151601_a((double) (chunkX + 8) / 200.0D, (double) (chunkZ + 8) / 200.0D);

        if (d0 < -0.8D) {
            this.theBiomeDecorator.flowersPerChunk = 15;
            this.theBiomeDecorator.grassPerChunk = 5;
        } else {
            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            // TODO: tall-flower placement (WorldGenDoublePlant) unavailable in MITE
        }

        // TODO: sunflower placement (WorldGenDoublePlant) unavailable in MITE
        super.decorate(world, rand, chunkX, chunkZ);
    }

    public BBiomes.BBiome createMutation() {
        BiomePlains mutation = new BiomePlains(this.biomeID + 128);
        mutation.name("Sunflower Plains");
        mutation.sunflower = true;
        mutation.color(9286496);
        mutation.field_150609_ah = 14273354;
        return mutation;
    }
}
