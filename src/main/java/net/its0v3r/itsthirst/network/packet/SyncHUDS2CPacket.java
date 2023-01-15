package net.its0v3r.itsthirst.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class SyncHUDS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {
        PlayerEntity playerEntity = client.player;
        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();
        thirstManager.setIsInHotBiomeForEnoughtTime(buffer.readBoolean());
    }
}
