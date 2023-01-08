package net.its0v3r.itsthirst.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class SyncThirstS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        // Get the int values from the buffer
        int[] bufferArray = buffer.readIntArray();
        int entityId = bufferArray[0];
        int thirstLevel = bufferArray[1];

        // Sync
        if (client.player != null && client.player.world.getEntityById(entityId) != null) {
            // Cast the client player to a PlayerEntity, this way I'll be able to access the ThirstManager defined in the "PlayerEntityMixin"
            PlayerEntity playerEntity = (PlayerEntity) client.player.world.getEntityById(entityId);

            // Get the ThirstManager
            ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

            // Set a new ThirstValue based on the sync
            thirstManager.setThirstLevel(thirstLevel);
        }
    }
}
