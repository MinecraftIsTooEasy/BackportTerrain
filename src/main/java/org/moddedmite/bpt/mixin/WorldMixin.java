package org.moddedmite.bpt.mixin;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.EnumSkyBlock;
import net.minecraft.Material;
import net.minecraft.World;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.moddedmite.bpt.world.biome.BBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow public abstract BiomeGenBase getBiomeGenForCoords(int par1, int par2);
	@Shadow public abstract int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4);
	@Shadow public abstract int getBlockId(int par1, int par2, int par3);
	@Shadow public abstract int getBlockMetadata(int x, int y, int z);
	@Shadow public abstract Material getBlockMaterial(int par1, int par2, int par3);
	@Shadow public abstract int getHeightValue(int par1, int par2);
	
	@Inject(method = "canSnowAt", at = @At("HEAD"), cancellable = true)
	private void canSnowAt(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		BiomeGenBase biomeBase = this.getBiomeGenForCoords(x, z);
		if (!(biomeBase instanceof BBiomes.BBiome biome)) return;
		if (biome.getFloatTemperature(x, y, z) > 0.15F) {
			cir.setReturnValue(false);
		} else if (y >= 0 && y < 256 && this.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10) {
			int below = this.getBlockId(x, y - 1, z);
			int here = this.getBlockId(x, y, z);
			if (Block.getBlock(below) == Block.tilledField && Block.getBlock(here) != Block.pumpkinStem) {
				cir.setReturnValue(true);
			} else if (here == 0 && Block.snow.isLegalAt(ReflectHelper.dyCast(this), x, y, z, 0) && below != Block.ice.blockID) {
				cir.setReturnValue(true);
			} else {
				cir.setReturnValue(false);
			}
		} else {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "canBlockFreeze", at = @At("HEAD"), cancellable = true)
	private void canBlockFreeze(int x, int y, int z, boolean byWater, CallbackInfoReturnable<Boolean> cir) {
		BiomeGenBase biomeBase = this.getBiomeGenForCoords(x, z);
		if (!(biomeBase instanceof BBiomes.BBiome biome)) return;
		if (biome.getFloatTemperature(x, y, z) > 0.15F) {
			cir.setReturnValue(false);
		} else if (y >= 0 && y < 256 && this.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10) {
			int id = this.getBlockId(x, y, z);
			if ((id == Block.waterStill.blockID || id == Block.waterMoving.blockID) && this.getBlockMetadata(x, y, z) == 0) {
				if (!byWater) {
					cir.setReturnValue(true);
					return;
				}
				boolean flag = true;
				if (flag && this.getBlockMaterial(x - 1, y, z) != Material.water) {
					flag = false;
				}
				if (flag && this.getBlockMaterial(x + 1, y, z) != Material.water) {
					flag = false;
				}
				if (flag && this.getBlockMaterial(x, y, z - 1) != Material.water) {
					flag = false;
				}
				if (flag && this.getBlockMaterial(x, y, z + 1) != Material.water) {
					flag = false;
				}
				cir.setReturnValue(!flag);
			} else {
				cir.setReturnValue(false);
			}
		} else {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "isFreezing", at = @At("HEAD"), cancellable = true)
	private void isFreezing(int x, int z, CallbackInfoReturnable<Boolean> cir) {
		BiomeGenBase biomeBase = this.getBiomeGenForCoords(x, z);
		if (!(biomeBase instanceof BBiomes.BBiome biome)) return;
		cir.setReturnValue(biome.getFloatTemperature(x, this.getHeightValue(x, z), z) <= 0.15F);
	}
}
