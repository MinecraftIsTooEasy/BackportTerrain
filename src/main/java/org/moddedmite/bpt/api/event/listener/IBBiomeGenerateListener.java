package org.moddedmite.bpt.api.event.listener;

import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import org.moddedmite.bpt.world.gen.layer.BGenLayerBiome;
import org.moddedmite.bpt.world.gen.layer.BGenLayerHills;
import org.moddedmite.bpt.world.gen.structure.MapGenStronghold;
import org.moddedmite.bpt.world.gen.structure.MapGenVillage;
import org.moddedmite.bpt.world.BWorldChunkManager;

import java.util.List;


/**
 * Listeners of the backport terrain biome generation chain.
 * <br>
 * These mirror the vanilla / RustedIronCore hooks, but target the backport classes
 * ({@link BGenLayerBiome}, {@link BGenLayerHills}, ...) which only run for the backport
 * world types.
 * <br>
 * You'd better make the method {@link GenLayer#nextInt} public, without it, you can do nothing about random.
 */
public interface IBBiomeGenerateListener {
    /**
     * Modifies the initial biome pools of {@link BGenLayerBiome}.
     * <br>
     * {@code hotBiomes} is used for hot climates, {@code warmBiomes} for warm, {@code coolBiomes} for cool
     * and {@code icyBiomes} for icy ones. If no mod modifies them, the generation is like vanilla.
     */
    default void onInitialBiomesModify(List<BiomeGenBase> hotBiomes,
                                       List<BiomeGenBase> warmBiomes,
                                       List<BiomeGenBase> coolBiomes,
                                       List<BiomeGenBase> icyBiomes) {
    }

    /**
     * Here the backport generation transforms some biomes into their hilly variants: {@link BGenLayerHills}
     *
     * @param original the base biome id that is being turned into a hilly variant.
     * @return the biome id that should replace the base one, or {@code original} to keep the default mapping.
     */
    default int onLayerHills(GenLayer genLayer, int original) {
        return original;
    }

    /**
     * Modifies the list of biomes strongholds may generate in: {@link MapGenStronghold#field_151546_e}
     */
    default void onStrongholdAllowedRegister(List<BiomeGenBase> original) {
    }

    /**
     * Modifies the list of biomes villages may generate in: {@link MapGenVillage#villageSpawnBiomes}
     */
    default void onVillageAllowedRegister(List<BiomeGenBase> original) {
    }

    /**
     * Modifies the list of biomes the player may spawn in: {@link BWorldChunkManager#getBiomesToSpawnIn()}
     */
    default void onPlayerSpawnableRegister(List<BiomeGenBase> original) {
    }
}
