package org.moddedmite.bpt.event;

import com.google.common.collect.Lists;
import moddedmite.rustedironcore.api.event.Handlers;
import net.minecraft.MapGenVillage;
import org.moddedmite.bpt.world.biome.BBiomes;

import java.util.ArrayList;

public class BEvent extends Handlers {

    public static void register() {
        ArrayList biomes = Lists.newArrayList(MapGenVillage.villageSpawnBiomes);
        biomes.add(BBiomes.plains);
        biomes.add(BBiomes.desert);
        biomes.add(BBiomes.savanna);
        MapGenVillage.villageSpawnBiomes = biomes;
    }
}
