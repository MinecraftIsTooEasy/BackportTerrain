package org.moddedmite.bpt.api.event.events;

import moddedmite.rustedironcore.api.world.Dimension;
import org.moddedmite.bpt.api.event.BPTHandler;
import org.moddedmite.bpt.world.gen.MapGenBase;
import org.moddedmite.bpt.world.gen.structure.MapGenStructure;

/**
 * Convenience facade over {@link BPTHandler#MapGen}, mirroring
 * {@code moddedmite.rustedironcore.api.event.events.MapGenRegisterEvent} for the backport world types.
 */
public class MapGenRegisterEvent {
    public void register(Dimension dimension, MapGenBase mapGen) {
        BPTHandler.MapGen.register(dimension, mapGen);
    }

    public void registerStructure(Dimension dimension, MapGenStructure structure) {
        BPTHandler.MapGen.registerStructure(dimension, structure);
    }
}
