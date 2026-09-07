package org.moddedmite.bpt;

import moddedmite.rustedironcore.api.util.IdUtilExtra;
import net.minecraft.WorldType;
import org.moddedmite.bpt.event.ExampleEvent;
import net.fabricmc.api.ModInitializer;

import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackportTerrain implements ModInitializer {
    public static final String MOD_ID = "bpt";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final WorldType BACKPORT_DEFAULT = new WorldType(IdUtilExtra.getNextWorldType(), "backport_default");
    public static final WorldType BACKPORT_LARGE_BIOMES = new WorldType(IdUtilExtra.getNextWorldType(), "backport_large_biomes");

    public static boolean isBackportWorldType(WorldType worldType) {
        return worldType == BACKPORT_DEFAULT || worldType == BACKPORT_LARGE_BIOMES;
    }

    public static boolean isBackportLargeBiomes(WorldType worldType) {
        return worldType == BACKPORT_LARGE_BIOMES;
    }

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
        ExampleEvent.register();
    }
}
