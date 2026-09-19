package org.moddedmite.bpt;

import moddedmite.rustedironcore.api.util.IdUtilExtra;
import net.fabricmc.api.ModInitializer;
import net.minecraft.WorldType;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moddedmite.bpt.event.BEvent;

public class BackportTerrain implements ModInitializer {
    public static final String MOD_ID = "bpt";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static WorldType BACKPORT_DEFAULT = new WorldType(IdUtilExtra.getNextWorldType(), "backport_default");
    public static WorldType BACKPORT_LARGE_BIOMES = new WorldType(IdUtilExtra.getNextWorldType(), "backport_large_biomes");
    public static WorldType AMPLIFIED = new WorldType(IdUtilExtra.getNextWorldType(), "amplified");

    public static boolean isBackportWorldType(WorldType worldType) {
        return worldType != null && (worldType == BACKPORT_DEFAULT || worldType == BACKPORT_LARGE_BIOMES || worldType == AMPLIFIED);
    }

    public static boolean isBackportLargeBiomes(WorldType worldType) {
        return worldType == BACKPORT_LARGE_BIOMES;
    }

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
        BEvent.register();
    }
}
