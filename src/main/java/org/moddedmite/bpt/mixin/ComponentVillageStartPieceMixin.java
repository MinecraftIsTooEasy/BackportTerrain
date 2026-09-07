package org.moddedmite.bpt.mixin;

import net.minecraft.BiomeGenBase;
import net.minecraft.ComponentVillageStartPiece;
import net.minecraft.WorldChunkManager;
import org.moddedmite.bpt.world.biome.BBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(ComponentVillageStartPiece.class)
public class ComponentVillageStartPieceMixin {
	@Shadow public boolean inDesert;

	@Inject(method = "<init>(Lnet/minecraft/WorldChunkManager;ILjava/util/Random;IILjava/util/List;I)V", at = @At("RETURN"))
	private void bpt_fixDesert(WorldChunkManager worldChunkManager, int i, Random random, int j, int k, List list, int l, CallbackInfo ci) {
		BiomeGenBase biome = worldChunkManager.getBiomeGenAt(j, k);
		if (biome == BBiomes.desert || biome == BBiomes.desertHills) {
			this.inDesert = true;
		}
	}
}
