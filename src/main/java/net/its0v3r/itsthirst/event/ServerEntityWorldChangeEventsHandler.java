package net.its0v3r.itsthirst.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.its0v3r.itsthirst.access.ServerPlayerAccess;
import net.its0v3r.itsthirst.identifier.NetworkPacketsIdentifiers;
import net.its0v3r.itsthirst.network.utils.defaultBuffers;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;

public class ServerEntityWorldChangeEventsHandler implements ServerEntityWorldChangeEvents.AfterPlayerChange {

    // Sync the thirst when the player change dimensions
    @Override
    public void afterChangeWorld(ServerPlayerEntity serverPlayerEntity, ServerWorld origin, ServerWorld destination) {
        ((ServerPlayerAccess) serverPlayerEntity).syncThirstAfterChangeDimension();
    }
}
