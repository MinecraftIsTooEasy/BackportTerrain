package org.moddedmite.bpt.mixin;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.World;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.moddedmite.bpt.world.biome.BBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BiomeGenBase.class)
public class BiomeGenBaseMixin {
	@Shadow
	public byte topBlock;
	
	@Shadow
	public byte fillerBlock;
	
	@Inject(method = "isJungleBiome", at = @At("HEAD"), cancellable = true)
	private void addJungleBiome(CallbackInfoReturnable<Boolean> cir) {
		if (ReflectHelper.dyCast(this) == BBiomes.jungle ||
				ReflectHelper.dyCast(this) == BBiomes.jungleEdge ||
				ReflectHelper.dyCast(this) == BBiomes.jungleHills)
			cir.setReturnValue(true);
	}
	
	@Inject(method = "isDesertBiome", at = @At("HEAD"), cancellable = true)
	private void addDesertBiome(CallbackInfoReturnable<Boolean> cir) {
		if (ReflectHelper.dyCast(this) == BBiomes.desert ||
				ReflectHelper.dyCast(this) == BBiomes.desertHills)
			cir.setReturnValue(true);
	}
	
	@Inject(method = "isSwampBiome", at = @At("HEAD"), cancellable = true)
	private void addSwampBiome(CallbackInfoReturnable<Boolean> cir) {
		if (ReflectHelper.dyCast(this) == BBiomes.swampland)
			cir.setReturnValue(true);
	}
	
	@Inject(method = "isHillyOrMountainous", at = @At("HEAD"), cancellable = true)
	private void addHillyOrMountainousBiome(CallbackInfoReturnable<Boolean> cir) {
		if (ReflectHelper.dyCast(this) == BBiomes.extremeHills ||
				ReflectHelper.dyCast(this) == BBiomes.iceMountains ||
				ReflectHelper.dyCast(this) == BBiomes.desertHills ||
				ReflectHelper.dyCast(this) == BBiomes.forestHills ||
				ReflectHelper.dyCast(this) == BBiomes.taigaHills ||
				ReflectHelper.dyCast(this) == BBiomes.extremeHillsEdge ||
				ReflectHelper.dyCast(this) == BBiomes.jungleHills ||
				ReflectHelper.dyCast(this) == BBiomes.birchForestHills ||
				ReflectHelper.dyCast(this) == BBiomes.coldTaigaHills ||
				ReflectHelper.dyCast(this) == BBiomes.extremeHillsPlus ||
				ReflectHelper.dyCast(this) == BBiomes.megaTaigaHills)
			cir.setReturnValue(true);
	}
}
