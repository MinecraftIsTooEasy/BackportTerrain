package org.moddedmite.bpt.mixin;

import net.minecraft.EntityList;
import net.minecraft.NetClientHandler;
import net.minecraft.Packet24MobSpawn;
import org.moddedmite.bpt.BackportTerrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {
	@Inject(method = "handleMobSpawn", at = @At("HEAD"), cancellable = true)
	private void guardMobSpawn(Packet24MobSpawn packet, CallbackInfo ci) {
		if (EntityList.getClassFromID(packet.type) == null) {
			ci.cancel();
		}
	}
}
