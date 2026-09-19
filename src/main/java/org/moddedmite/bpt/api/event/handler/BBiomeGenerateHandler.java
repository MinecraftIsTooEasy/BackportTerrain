package org.moddedmite.bpt.api.event.handler;

import moddedmite.rustedironcore.api.event.AbstractHandler;
import moddedmite.rustedironcore.api.event.Handlers;
import net.minecraft.BiomeGenBase;
import net.minecraft.GenLayer;
import org.moddedmite.bpt.api.event.listener.IBBiomeGenerateListener;

import java.util.List;

public class BBiomeGenerateHandler extends AbstractHandler<IBBiomeGenerateListener> {
    public void onInitialBiomesModify(List<BiomeGenBase> hotBiomes, List<BiomeGenBase> warmBiomes, List<BiomeGenBase> coolBiomes, List<BiomeGenBase> icyBiomes) {
        this.listeners.forEach(x -> x.onInitialBiomesModify(hotBiomes, warmBiomes, coolBiomes, icyBiomes));
        Handlers.BiomeGenerate.onInitialBiomesModify(hotBiomes);
        Handlers.BiomeGenerate.onInitialBiomesModify(warmBiomes);
        Handlers.BiomeGenerate.onInitialBiomesModify(coolBiomes);
        Handlers.BiomeGenerate.onInitialBiomesModify(icyBiomes);
    }

    public int onLayerHills(GenLayer genLayer, int original) {
        for (IBBiomeGenerateListener listener : this.listeners) {
            original = listener.onLayerHills(genLayer, original);
        }
        return Handlers.BiomeGenerate.onLayerHills(genLayer, original);
    }

    public void onStrongholdAllowedRegister(List<BiomeGenBase> original) {
        this.listeners.forEach(x -> x.onStrongholdAllowedRegister(original));
        Handlers.BiomeGenerate.onStrongholdAllowedRegister(original);
    }

    public void onVillageAllowedRegister(List<BiomeGenBase> original) {
        this.listeners.forEach(x -> x.onVillageAllowedRegister(original));
        Handlers.BiomeGenerate.onVillageAllowedRegister(original);
    }

    public void onPlayerSpawnableRegister(List<BiomeGenBase> original) {
        this.listeners.forEach(x -> x.onPlayerSpawnableRegister(original));
        Handlers.BiomeGenerate.onPlayerSpawnableRegister(original);
    }
}
