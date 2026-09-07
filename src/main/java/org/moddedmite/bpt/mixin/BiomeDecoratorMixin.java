package org.moddedmite.bpt.mixin;

import net.minecraft.BiomeDecorator;
import net.minecraft.BiomeGenBase;
import net.minecraft.World;
import org.moddedmite.bpt.api.IBiomeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public abstract class BiomeDecoratorMixin implements IBiomeDecorator {
	@Shadow protected World currentWorld;
	@Shadow protected Random randomGenerator;
	@Shadow protected int chunk_X;
	@Shadow protected int chunk_Z;
	@Shadow protected abstract void decorate();

	// remove vanilla mite randomGenerator.setSeed
	public void decorateChunk(World world, Random rand, BiomeGenBase biome, int chunkX, int chunkZ) {
		if (this.currentWorld != null) {
			throw new RuntimeException("Already decorating!!");
		} else {
			this.currentWorld = world;
			this.randomGenerator = rand;
			this.chunk_X = chunkX;
			this.chunk_Z = chunkZ;
			this.decorate();
			this.currentWorld = null;
			this.randomGenerator = null;
		}
	}
}
