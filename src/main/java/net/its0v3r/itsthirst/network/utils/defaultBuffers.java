package net.its0v3r.itsthirst.network.utils;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class defaultBuffers {
    public static PacketByteBuf createSyncThirstDefaultBuffer(ServerPlayerEntity serverPlayerEntity) {
        // Create a new buffer with a int array, where will take the Player ID (int) and the current player thristLevel (int)
        PacketByteBuf buffer = PacketByteBufs.create();

        buffer.writeIntArray(new int[]{
                serverPlayerEntity.getId(), ((ThirstManagerAccess) serverPlayerEntity).getThirstManager().getThirstLevel()
        });

        return buffer;
    }
}
