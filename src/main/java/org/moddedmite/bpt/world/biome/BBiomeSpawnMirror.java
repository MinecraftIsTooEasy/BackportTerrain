package org.moddedmite.bpt.world.biome;

import moddedmite.rustedironcore.api.util.BiomeSpawnUtil;
import net.minecraft.BiomeGenBase;
import net.minecraft.EntityList;
import net.minecraft.EnumCreatureType;
import net.minecraft.SpawnListEntry;
import org.moddedmite.bpt.BackportTerrain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BBiomeSpawnMirror {
    private static final EnumCreatureType[] CREATURE_TYPES = {
            EnumCreatureType.monster,
            EnumCreatureType.animal,
            EnumCreatureType.aquatic,
            EnumCreatureType.ambient
    };

    private static final Map<BBiomes.BBiome, BiomeGenBase> COUNTERPARTS;

    private static boolean applied;

    static {
        Map<BBiomes.BBiome, BiomeGenBase> map = new LinkedHashMap<>();
        map.put(BBiomes.ocean, BiomeGenBase.ocean);
        map.put(BBiomes.deepOcean, BiomeGenBase.ocean);
        map.put(BBiomes.frozenOcean, BiomeGenBase.frozenOcean);
        map.put(BBiomes.river, BiomeGenBase.river);
        map.put(BBiomes.frozenRiver, BiomeGenBase.frozenRiver);
        map.put(BBiomes.beach, BiomeGenBase.beach);
        map.put(BBiomes.coldBeach, BiomeGenBase.beach);
        map.put(BBiomes.stoneBeach, BiomeGenBase.beach);
        map.put(BBiomes.plains, BiomeGenBase.plains);
        map.put(BBiomes.savanna, BiomeGenBase.plains);
        map.put(BBiomes.savannaPlateau, BiomeGenBase.plains);
        map.put(BBiomes.desert, BiomeGenBase.desert);
        map.put(BBiomes.desertHills, BiomeGenBase.desertHills);
        map.put(BBiomes.mesa, BiomeGenBase.desert);
        map.put(BBiomes.mesaPlateau, BiomeGenBase.desert);
        map.put(BBiomes.mesaPlateau_F, BiomeGenBase.desert);
        map.put(BBiomes.forest, BiomeGenBase.forest);
        map.put(BBiomes.forestHills, BiomeGenBase.forestHills);
        map.put(BBiomes.birchForest, BiomeGenBase.forest);
        map.put(BBiomes.birchForestHills, BiomeGenBase.forestHills);
        map.put(BBiomes.roofedForest, BiomeGenBase.forest);
        map.put(BBiomes.extremeHills, BiomeGenBase.extremeHills);
        map.put(BBiomes.extremeHillsEdge, BiomeGenBase.extremeHillsEdge);
        map.put(BBiomes.extremeHillsPlus, BiomeGenBase.extremeHills);
        map.put(BBiomes.taiga, BiomeGenBase.taiga);
        map.put(BBiomes.taigaHills, BiomeGenBase.taigaHills);
        map.put(BBiomes.coldTaiga, BiomeGenBase.taiga);
        map.put(BBiomes.coldTaigaHills, BiomeGenBase.taigaHills);
        map.put(BBiomes.megaTaiga, BiomeGenBase.taiga);
        map.put(BBiomes.megaTaigaHills, BiomeGenBase.taigaHills);
        map.put(BBiomes.swampland, BiomeGenBase.swampland);
        map.put(BBiomes.icePlains, BiomeGenBase.icePlains);
        map.put(BBiomes.iceMountains, BiomeGenBase.iceMountains);
        map.put(BBiomes.jungle, BiomeGenBase.jungle);
        map.put(BBiomes.jungleHills, BiomeGenBase.jungleHills);
        map.put(BBiomes.jungleEdge, BiomeGenBase.jungle);
        COUNTERPARTS = Collections.unmodifiableMap(map);
    }

    private BBiomeSpawnMirror() {
    }

    public static synchronized void apply() {
        if (applied) {
            return;
        }
        applied = true;
        for (BiomeGenBase biome : BiomeGenBase.biomeList) {
            if (!(biome instanceof BBiomes.BBiome bptBiome)) {
                continue;
            }
            BBiomes.BBiome base = bptBiome instanceof BiomeGenMutated mutated ? mutated.baseBiome : bptBiome;
            BiomeGenBase counterpart = COUNTERPARTS.get(base);
            if (counterpart != null) {
                mirror(bptBiome, counterpart);
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    private static void mirror(BBiomes.BBiome target, BiomeGenBase source) {
        for (EnumCreatureType type : CREATURE_TYPES) {
            List<SpawnListEntry> targetList = target.getSpawnableList(type);
            if (targetList == null) {
                continue;
            }
            for (SpawnListEntry entry : (List<SpawnListEntry>) source.getSpawnableList(type)) {
                if (EntityList.getEntityID(entry.entityClass) == 0) {
                    continue;
                }
                if (!hasEntity(targetList, entry.entityClass)) {
                    BiomeSpawnUtil.addSpawn(entry.entityClass, entry.itemWeight, entry.minGroupCount, entry.maxGroupCount, type, target);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean hasEntity(List<SpawnListEntry> list, Class entityClass) {
        for (SpawnListEntry entry : list) {
            if (entry.entityClass == entityClass) {
                return true;
            }
        }
        return false;
    }
}
