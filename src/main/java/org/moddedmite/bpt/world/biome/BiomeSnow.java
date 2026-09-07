package org.moddedmite.bpt.world.biome;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenTaiga2;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class BiomeSnow extends BBiomes.BBiome {
    private final boolean superIcy;

    public BiomeSnow(int id, boolean superIcy) {
        super(id);
        this.superIcy = superIcy;

        if (superIcy) {
            this.surface(Block.snow, Block.snow);
        }

        this.spawnableCreatureList.clear();
    }

    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
        return new WorldGenTaiga2(false);
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ) {
        if (this.superIcy) {
            // TODO: ice spike placement (WorldGenIceSpike) unavailable in MITE
            // TODO: ice path placement (WorldGenIcePath) unavailable in MITE
        }
        super.decorate(world, rand, chunkX, chunkZ);
    }

    public BBiomes.BBiome createMutation() {
        BiomeSnow mutation = new BiomeSnow(this.biomeID + 128, true);
        mutation.setColor(13828095, true);
        mutation.name(this.biomeName + " Spikes");
        mutation.snow();
        mutation.tempRain(0.0F, 0.5F);
        mutation.height(new BBiomes.Height(this.minHeight + 0.1F, this.maxHeight + 0.1F));
        mutation.minHeight = this.minHeight + 0.3F;
        mutation.maxHeight = this.maxHeight + 0.4F;
        return mutation;
    }
}
