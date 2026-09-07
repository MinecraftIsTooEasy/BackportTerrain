package org.moddedmite.bpt.world.gen.layer;

import org.moddedmite.bpt.BackportTerrain;
import org.moddedmite.bpt.world.biome.BBiomes;
import net.minecraft.GenLayer;
import net.minecraft.WorldType;

public abstract class BGenLayer extends GenLayer {
    public BGenLayer(long seed) {
        super(seed);
    }
    
    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType) {
        BGenLayerIsland island = new BGenLayerIsland(1L);
        BGenLayerFuzzyZoom fuzzyZoom = new BGenLayerFuzzyZoom(2000L, island);
        BGenLayerAddIsland addIsland = new BGenLayerAddIsland(1L, fuzzyZoom);
        BGenLayerZoom zoom = new BGenLayerZoom(2001L, addIsland);
        addIsland = new BGenLayerAddIsland(2L, zoom);
        addIsland = new BGenLayerAddIsland(50L, addIsland);
        addIsland = new BGenLayerAddIsland(70L, addIsland);
        BGenLayerRemoveTooMuchOcean removeOcean = new BGenLayerRemoveTooMuchOcean(2L, addIsland);
        BGenLayerAddSnow addSnow = new BGenLayerAddSnow(2L, removeOcean);
        addIsland = new BGenLayerAddIsland(3L, addSnow);
        BGenLayerEdge edge = new BGenLayerEdge(2L, addIsland, BGenLayerEdge.Mode.COOL_WARM);
        edge = new BGenLayerEdge(2L, edge, BGenLayerEdge.Mode.HEAT_ICE);
        edge = new BGenLayerEdge(3L, edge, BGenLayerEdge.Mode.SPECIAL);
        zoom = new BGenLayerZoom(2002L, edge);
        zoom = new BGenLayerZoom(2003L, zoom);
        addIsland = new BGenLayerAddIsland(4L, zoom);
        BGenLayerAddMushroomIsland mushroomIsland = new BGenLayerAddMushroomIsland(5L, addIsland);
        BGenLayerDeepOcean deepOcean = new BGenLayerDeepOcean(4L, mushroomIsland);
        
        GenLayer biomeLayer = BGenLayerZoom.magnify(1000L, deepOcean, 0);
        byte biomeSize = 4;
        
        if (worldType == WorldType.LARGE_BIOMES || BackportTerrain.isBackportLargeBiomes(worldType)) {
            biomeSize = 6;
        }
        
        GenLayer riverBase = BGenLayerZoom.magnify(1000L, biomeLayer, 0);
        BGenLayerRiverInit riverInit = new BGenLayerRiverInit(100L, riverBase);
        GenLayer biomeChain = new BGenLayerBiome(200L, biomeLayer, worldType);
        GenLayer riverZoomed = BGenLayerZoom.magnify(1000L, riverInit, 2);
        BGenLayerHills hills = new BGenLayerHills(1000L, biomeChain, riverZoomed);
        riverBase = BGenLayerZoom.magnify(1000L, riverInit, 2);
        riverBase = BGenLayerZoom.magnify(1000L, riverBase, biomeSize);
        BGenLayerRiver river = new BGenLayerRiver(1L, riverBase);
        BGenLayerSmooth smoothRiver = new BGenLayerSmooth(1000L, river);
        biomeChain = new BGenLayerRareBiome(1001L, hills);
        
        for (int j = 0; j < biomeSize; ++j) {
            biomeChain = new BGenLayerZoom((long) (1000 + j), biomeChain);
            
            if (j == 0) {
                biomeChain = new BGenLayerAddIsland(3L, biomeChain);
            }
            
            if (j == 1) {
                biomeChain = new BGenLayerShore(1000L, biomeChain);
            }
        }
        
        BGenLayerSmooth smoothBiomes = new BGenLayerSmooth(1000L, biomeChain);
        BGenLayerRiverMix riverMix = new BGenLayerRiverMix(100L, smoothBiomes, smoothRiver);
        BGenLayerVoronoiZoom voronoiZoom = new BGenLayerVoronoiZoom(10L, riverMix);
        riverMix.initWorldGenSeed(seed);
        voronoiZoom.initWorldGenSeed(seed);
        return new GenLayer[]{riverMix, voronoiZoom, riverMix};
    }
    
    protected int selectRandom(int... values) {
        return values[this.nextInt(values.length)];
    }
    
    protected int selectModeOrRandom(int a, int b, int c, int d) {
        return b == c && c == d ? b : (a == b && a == c ? a : (a == b && a == d ? a : (a == c && a == d ? a : (a == b && c != d ? a : (a == c && b != d ? a : (a == d && b != c ? a : (b == c && a != d ? b : (b == d && a != c ? b : (c == d && a != b ? c : this.selectRandom(new int[]{a, b, c, d}))))))))));
    }
    
    protected static boolean compareBiomesById(int a, int b) {
        if (a == b) {
            return true;
        }
        
        if (a != BBiomes.mesaPlateau_F.biomeID && a != BBiomes.mesaPlateau.biomeID) {
            return false;
        }
        
        return b == BBiomes.mesaPlateau_F.biomeID || b == BBiomes.mesaPlateau.biomeID;
    }
    
    protected static boolean isBiomeOceanic(int id) {
        return id == BBiomes.ocean.biomeID || id == BBiomes.deepOcean.biomeID || id == BBiomes.frozenOcean.biomeID;
    }
}
