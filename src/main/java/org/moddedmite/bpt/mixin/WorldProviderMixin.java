package org.moddedmite.bpt.mixin;

import net.minecraft.IChunkProvider;
import net.minecraft.World;
import net.minecraft.WorldChunkManager;
import org.moddedmite.bpt.BackportTerrain;
import org.moddedmite.bpt.world.BChunkProviderGenerate;
import org.moddedmite.bpt.world.BWorldChunkManager;
import net.minecraft.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldProvider.class)
public class WorldProviderMixin {
    @Shadow public World worldObj;
    @Shadow public WorldChunkManager worldChunkMgr;
    
    @Inject(method = "createChunkGenerator", at = @At("HEAD"), cancellable = true)
    private void createChunkGenerator(CallbackInfoReturnable<IChunkProvider> cir) {
        if (BackportTerrain.isBackportWorldType(this.worldObj.getWorldInfo().getTerrainType())) {
            cir.setReturnValue(new BChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()));
        }
    }
    
    @Inject(method = "registerWorldChunkManager", at = @At("TAIL"))
    private void registerWorldChunkManager(CallbackInfo ci) {
        if (BackportTerrain.isBackportWorldType(this.worldObj.getWorldInfo().getTerrainType())) {
            this.worldChunkMgr = new BWorldChunkManager(this.worldObj);
        }
    }
}
