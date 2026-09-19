package org.moddedmite.bpt.world.gen;

import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.world.Dimension;
import net.minecraft.Block;
import net.minecraft.IChunkProvider;
import net.minecraft.World;

import java.util.Random;

public final class RICMapGenBridge {

    private RICMapGenBridge() {
    }

    public static void onChunkProvideMapGen(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ, Block[] blocks) {
        byte[] vanillaBlocks = toVanillaBlocks(blocks);
        Handlers.MapGen.onChunkProvideMapGen(dimension, chunkProvider, world, chunkX, chunkZ, vanillaBlocks);
        applyVanillaBlocks(blocks, vanillaBlocks);
    }

    public static void onChunkProvideStructures(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ, Block[] blocks) {
        byte[] vanillaBlocks = toVanillaBlocks(blocks);
        Handlers.MapGen.onChunkProvideStructures(dimension, chunkProvider, world, chunkX, chunkZ, vanillaBlocks);
        applyVanillaBlocks(blocks, vanillaBlocks);
    }

    public static void onRecreateStructures(Dimension dimension, IChunkProvider chunkProvider, World world, int chunkX, int chunkZ) {
        Handlers.MapGen.onRecreateStructures(dimension, chunkProvider, world, chunkX, chunkZ);
    }

    public static void onChunkPopulateStructures(Dimension dimension, World world, Random rand, int chunkX, int chunkZ) {
        Handlers.MapGen.onChunkPopulateStructures(dimension, world, rand, chunkX, chunkZ);
    }

    private static byte[] toVanillaBlocks(Block[] blocks) {
        byte[] vBlocks = new byte[16 * 16 * 128];
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int bBase = (x * 16 + z) * 256;
                int vBase = (x * 16 + z) * 128;
                for (int y = 0; y < 128; ++y) {
                    Block block = blocks[bBase + y];
                    vBlocks[vBase + y] = block == null ? 0 : (byte) block.blockID;
                }
            }
        }

        return vBlocks;
    }

    private static void applyVanillaBlocks(Block[] blocks, byte[] vanillaBlocks) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int bBase = (x * 16 + z) * 256;
                int vBase = (x * 16 + z) * 128;
                for (int y = 0; y < 128; ++y) {
                    int newId = vanillaBlocks[vBase + y] & 255;
                    Block current = blocks[bBase + y];
                    int currentId = current == null ? 0 : current.blockID;
                    if (newId != currentId) {
                        blocks[bBase + y] = Block.getBlock(newId);
                    }
                }
            }
        }
    }
}
