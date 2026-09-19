package org.moddedmite.bpt.api.event;

import org.moddedmite.bpt.api.event.handler.BBiomeGenerateHandler;
import org.moddedmite.bpt.api.event.handler.BMapGenHandler;

/**
 * Static access point for the events fired by the backport terrain generation pipeline.
 * <br>
 * The vanilla / RustedIronCore event hooks only cover the default MITE world types.
 * Since BackportTerrain uses its own chunk provider and {@link net.minecraft.GenLayer} chain
 * (see {@code org.moddedmite.bpt.world}), content registered here only applies to the
 * backport world types.
 */
public final class BPTHandler {
    private BPTHandler() {
    }

    /**
     * Register additional map generators / structures that run inside
     * {@link org.moddedmite.bpt.world.BChunkProviderGenerate}.
     */
    public static final BMapGenHandler MapGen = new BMapGenHandler();

    /**
     * Modify which biomes the backport generation chain may pick, and which biomes are valid
     * for structures / player spawning.
     */
    public static final BBiomeGenerateHandler BiomeGenerate = new BBiomeGenerateHandler();
}
